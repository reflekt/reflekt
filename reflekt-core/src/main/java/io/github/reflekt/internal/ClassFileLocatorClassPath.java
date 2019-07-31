package io.github.reflekt.internal;

import static java.util.stream.Collectors.toSet;

import io.github.reflekt.ClassFileLocator;
import io.github.reflekt.ReflektConf;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ClassFileLocatorClassPath implements ClassFileLocator {

    private static final Logger LOG = Logger.getLogger(ClassFileLocatorClassPath.class.getCanonicalName());

    private final String packageFilter;
    private final String packageFileMatcher;
    private Map<Boolean, Set<String>> keeper = new ConcurrentHashMap<>();
    private final List<String> classResourceDirs;

    ClassFileLocatorClassPath(ReflektConf conf) {
        packageFilter = conf.getPackageFilter();
        packageFileMatcher = packageFilter.replace('.', File.separatorChar);
        classResourceDirs = conf.getClassResourceDirs();
    }

    @Override
    public Set<String> getClasses(boolean includeNestedJars) {
        return keeper.computeIfAbsent(false, b -> initialize());
    }

    private Set<String> initialize() {
        try {
            Stream<File> rootFiles = getRoots();
            return initialize(rootFiles);
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Was unable to get class files from classpath", e);
        }
        return Collections.emptySet();
    }

    private static <T> Stream<T> asStream(Enumeration<T> e) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        new Iterator<T>() {
                            public T next() {
                                return e.nextElement();
                            }

                            public boolean hasNext() {
                                return e.hasMoreElements();
                            }
                        }, Spliterator.ORDERED), false);
    }

    private Stream<File> getRoots() throws IOException {
        if (classResourceDirs == null) {
            Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(".");
            return asStream(urlEnumeration)
                    .map(u -> {
                        try {
                            return new File(u.toURI());
                        } catch (URISyntaxException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull);
        } else {
            return classResourceDirs.stream().map(File::new);
        }
    }

    Set<String> initialize(Stream<File> rootFiles) {
        return rootFiles.map(FileWithRoot::new)
                .map(this::travel)
                .flatMap(List::stream)
                .map(FileWithRoot::asClassRef)
                .filter(a -> a.startsWith(packageFilter))
                .collect(toSet());
    }

    private List<FileWithRoot> travel(FileWithRoot root) {
        return travel(Collections.singletonList(root), Collections.emptyList());
    }

    private List<FileWithRoot> travel(List<FileWithRoot> lookingAt, List<FileWithRoot> found) {
        if (lookingAt.isEmpty()) {
            return found;
        }
        List<FileWithRoot> files = lookingAt.stream()
                .filter(FileWithRoot::evenBother)
                .flatMap(this::files)
                .collect(Collectors.toList());
        List<FileWithRoot> dirs = files.stream()
                .filter(f -> f.file.isDirectory())
                .collect(Collectors.toList());
        List<FileWithRoot> classes = files.stream()
                .filter(this::isClassFile)
                .collect(Collectors.toList());
        classes.addAll(found);
        return travel(dirs, classes);
    }

    private Stream<FileWithRoot> files(FileWithRoot file) {
        File[] files = Objects.requireNonNull(file.file.listFiles());
        return Arrays.stream(files).map(f -> new FileWithRoot(file.root, f));
    }

    private boolean isClassFile(FileWithRoot file) {
        return file.file.isFile() && ClassFilePattern.isClassFile(file.getRelevantPath());
    }

    private class FileWithRoot {
        final File root;
        final File file;

        FileWithRoot(File root) {
            this(root, root);
        }

        FileWithRoot(File root, File file) {
            this.root = root;
            this.file = file;
        }

        private String getRelevantPath() {
            return file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1);
        }

        private boolean evenBother() {
            if (packageFileMatcher.isEmpty()) {
                return true;
            } else if (root.equals(file)) {
                return true;
            } else {
                String relevantPath = getRelevantPath();
                if (relevantPath.length() < packageFileMatcher.length()) {
                    return relevantPath.equals(packageFileMatcher.substring(0, relevantPath.length()));
                } else {
                    return relevantPath.startsWith(packageFileMatcher);
                }
            }
        }

        private String asClassRef() {
            int initalDrop = root.getAbsolutePath().length() + 1;
            String trimmedStart = file.getAbsolutePath().substring(initalDrop);
            String trimmedClass = trimmedStart.substring(0, trimmedStart.length() - 6);
            return trimmedClass.replace(File.separatorChar, '.')
                    .replace('$', '.');

        }

    }
}
