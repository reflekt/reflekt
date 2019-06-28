package io.github.reflekt.internal;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.list;
import static java.util.stream.Collectors.toList;

import io.github.reflekt.ClassFileLocator;
import io.github.reflekt.ReflektConf;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
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

class ClassFileLocatorJar implements ClassFileLocator {

    private static final Logger LOG = Logger.getLogger(ClassFileLocatorJar.class.getCanonicalName());

    private final String packageFilter;
    private final Map<Boolean, Set<String>> keeper = new ConcurrentHashMap<>();

    ClassFileLocatorJar(ReflektConf conf) {
        this.packageFilter = conf.getPackageFilter();
    }

    @Override
    public Set<String> getClasses(boolean includeNestedJars) {
        return keeper.computeIfAbsent(includeNestedJars, this::getClassesFromSelfJar);
    }

    static Set<String> getClassesFromNestedJars(URI uri, String packageFilter, boolean includeNestedJars) {
        try (ZipFile zip = new ZipFile(new File(uri))) {
            List<? extends ZipEntry> entries = list(zip.entries());
            Set<String> classes = getClassesFromNestedJars(zip, entries, new HashSet<>(), includeNestedJars);
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
        Set<String> classRefs = getClasses(entries);
        if (!includeNestedJars) {
            return classRefs;
        }
        found.addAll(classRefs);
        List<ZipEntry> jars = entries.stream()
                .flatMap(e -> getNestedJarEntries(file, e).stream())
                .collect(toList());
        return getClassesFromNestedJars(file, jars, found, true);
    }

    private static boolean isClassRef(ZipEntry entry) {
        String name = entry.getName();
        return ClassFilePattern.isClassFile(name);
    }

    private static Set<String> getClasses(List<? extends ZipEntry> entries) {
        return entries.stream()
                .filter(ClassFileLocatorJar::isClassRef)
                .map(ClassFileLocatorJar::toClassRef)
                .collect(Collectors.toSet());
    }

    private static String toClassRef(ZipEntry entry) {
        String replace = entry.getName()
                .replace('/', '.')
                .replace('$', '.');
        if (replace.startsWith(".")) {
            replace = replace.substring(1);
        }
        return replace.substring(0, replace.length() - 6);
    }

    private static List<ZipEntry> getNestedJarEntries(ZipFile file, ZipEntry entry) {
        if (!entry.isDirectory() && entry.getName().endsWith(".jar")) {
            try (ZipInputStream is = new ZipInputStream(file.getInputStream(entry))) {
                ArrayList<ZipEntry> list = new ArrayList<>();
                ZipEntry next = is.getNextEntry();
                while (next != null) {
                    list.add(next);
                    next = is.getNextEntry();
                }
                return list;
            } catch (IOException e) {
                LOG.log(Level.WARNING, "Was unable to extract zip entries from zip file", e);
            }
        }
        return emptyList();
    }

    private Set<String> getClassesFromSelfJar(boolean includeNestedJars) {
        try {
            CodeSource source = ClassFileLocatorJar.class.getProtectionDomain().getCodeSource();
            URL location = source.getLocation();
            URI uri = location.toURI();
            if (uri.toString().endsWith(".jar")) {
                return getClassesFromNestedJars(uri, packageFilter, includeNestedJars);
            }
        } catch (NullPointerException | URISyntaxException e) {
            LOG.log(Level.WARNING, "Was unable to get classes from within JAR.", e);
        }
        return Collections.emptySet();
    }
}
