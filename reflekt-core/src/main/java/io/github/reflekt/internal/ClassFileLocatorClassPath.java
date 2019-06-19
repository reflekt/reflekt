package io.github.reflekt.internal;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import io.github.reflekt.ClassFileLocator;
import io.github.reflekt.ReflektConf;

public class ClassFileLocatorClassPath implements ClassFileLocator {

    private static final Logger LOG = Logger.getLogger(ClassFileLocatorClassPath.class.getCanonicalName());

    private static final String CLASS_NAME_MATCHER = "^.*/([A-Za-z0-9$])*[A-Za-z0-9]+\\.class$";
    private final String packageFilter;
    private final String packageFileMatcher;
    private Map<Boolean, Set<String>> keeper = new ConcurrentHashMap<>();

    ClassFileLocatorClassPath(ReflektConf conf) {
        packageFilter = conf.getPackageFilter();
        packageFileMatcher = packageFilter.replace('.', File.separatorChar);
    }

    @Override
    public Set<String> getClasses(boolean includeNestedJars) {
        return keeper.computeIfAbsent(false, b -> initialize());
    }

    private Set<String> initialize() {
        try {
            Iterator<URL> urlIterator = Thread.currentThread().getContextClassLoader().getResources("./").asIterator();
            Stream<File> rootFiles = StreamSupport.stream(Spliterators.spliteratorUnknownSize(urlIterator, 0), true)
                    .map(u -> {
                        try {
                            return new File(u.toURI());
                        } catch (URISyntaxException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull);
            return initialize(rootFiles);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Was unable to get class files from classpath", e);
        }
        return Collections.emptySet();
    }

    Set<String> initialize(Stream<File> rootFiles){
        return rootFiles.map(FileWithRoot::new)
                .map(this::travel)
                .flatMap(List::stream)
                .map(FileWithRoot::asClassRef)
                .filter(a -> a.startsWith(packageFilter))
                .collect(Collectors.toSet());
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
                .filter(f -> isClassFile(f.file))
                .collect(Collectors.toList());
        classes.addAll(found);
        return travel(dirs, classes);
    }

    private Stream<FileWithRoot> files(FileWithRoot file) {
        File[] files = Objects.requireNonNull(file.file.listFiles());
        return Arrays.stream(files).map(f -> new FileWithRoot(file.root, f));
    }

    private boolean isClassFile(File file) {
        return file.isFile() && file.getPath().matches(CLASS_NAME_MATCHER);
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

        private boolean evenBother() {
            if(packageFileMatcher.isEmpty()){
                return true;
            }else if (root.equals(file)){
                return true;
            }else {
                var relevantPath = file.getAbsolutePath().substring(root.getAbsolutePath().length()+1);
                if(relevantPath.length() < packageFileMatcher.length()){
                    return relevantPath.equals(packageFileMatcher.substring(0, relevantPath.length()));
                }else{
                    return relevantPath.startsWith(packageFileMatcher);
                }
            }
        }

        private String asClassRef() {
            int initalDrop = root.getAbsolutePath().length()+1;
            String trimmedStart = file.getAbsolutePath().substring(initalDrop);
            String trimmedClass = trimmedStart.substring(0, trimmedStart.length() - 6);
            return trimmedClass.replace('/', '.')
                    .replace(File.separatorChar, '.')
                    .replace('$', '.');

        }

    }
}
