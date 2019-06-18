package se.jensim.reflekt.internal;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.list;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import se.jensim.reflekt.ClassFileLocator;
import se.jensim.reflekt.ReflektConf;

class ClassFileLocatorJar implements ClassFileLocator {

    private Logger LOG = Logger.getLogger(getClass().getCanonicalName());
    private static final String CLASS_MATCHER = "^(/[A-Za-z0-9]+)+/([A-Za-z0-9]+[$]?)*[a-z]+\\.class$";

    private final String packageFilter;
    private final Map<Boolean, Set<String>> keeper = new ConcurrentHashMap<>();


    ClassFileLocatorJar(ReflektConf conf) {
        this.packageFilter = conf.getPackageFilter();
    }

    @Override
    public Set<String> getClasses(boolean includeNestedJars) {
        return keeper.computeIfAbsent(includeNestedJars, this::getClassesFromSelfJar);
    }

    private Set<String> getClassesFromSelfJar(boolean includeNestedJars) {
        try {
            var source = ClassFileLocatorJar.class.getProtectionDomain().getCodeSource();
            var location = source.getLocation();
            var uri = location.toURI();
            if (uri.toString().endsWith(".jar")) {
                return getClassesFromNestedJars(uri, packageFilter, includeNestedJars);
            }
        } catch (NullPointerException | URISyntaxException e) {
            LOG.log(Level.WARNING, "Was unable to get classes from within JAR.", e);
        }
        return Collections.emptySet();
    }

    static Set<String> getClassesFromNestedJars(URI uri, String packageFilter, boolean includeNestedJars) {
        try (var zip = new ZipFile(new File(uri))) {
            var entries = list(zip.entries());
            var classes = getClassesFromNestedJars(zip, entries, new HashSet<>(), includeNestedJars);
            return classes.stream()
                    .filter(s -> s.startsWith(packageFilter))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            return emptySet();
        }
    }

    private static Set<String> getClassesFromNestedJars(ZipFile file, List<? extends ZipEntry> entries, Set<String> found, boolean includeNestedJars) {
        if (entries.isEmpty()) {
            return found;
        }
        var classRefs = getClasses(entries);
        if (!includeNestedJars) {
            return classRefs;
        }
        found.addAll(classRefs);
        var jars = entries.stream()
                .flatMap(e -> getNestedJarEntries(file, e).stream())
                .collect(toList());
        return getClassesFromNestedJars(file, jars, found, true);
    }

    private static Set<String> getClasses(List<? extends ZipEntry> entries) {
        return entries.stream()
                .filter(ClassFileLocatorJar::isClassRef)
                .map(ClassFileLocatorJar::toClassRef)
                .collect(Collectors.toSet());
    }

    private static boolean isClassRef(ZipEntry entry) {
        return entry.getName().matches(CLASS_MATCHER);

    }

    private static String toClassRef(ZipEntry entry) {
        String replace = entry.getName()
                .replace('/', '.')
                .replace('$', '.');
        return replace.substring(1, replace.length() - 6);
    }

    private static List<ZipEntry> getNestedJarEntries(ZipFile file, ZipEntry entry) {
        if (!entry.isDirectory() && entry.getName().endsWith(".jar")) {
            try (var is = new ZipInputStream(file.getInputStream(entry))) {
                var list = new ArrayList<ZipEntry>();
                var next = is.getNextEntry();
                while (next != null) {
                    list.add(next);
                    next = is.getNextEntry();
                }
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return emptyList();
    }
}
