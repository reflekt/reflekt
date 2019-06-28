package io.github.reflekt.internal;

import static java.util.stream.Collectors.toSet;

import io.github.reflekt.ReflektAllTypes;
import io.github.reflekt.ReflektConf;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

class ReflektAllClassesImpl implements ReflektAllClasses {

    private static final int PARALLEL_CLASS_LOAD_BREAKPOINT = 24;
    private final Supplier<Set<Class>> keeper = LazyBuilder.lazy(this::init);
    private final ReflektConf conf;
    private final Supplier<ReflektAllTypes> reflektAllTypes;
    private ClassLoader classLoader;

    ReflektAllClassesImpl(ReflektConf conf, Supplier<ReflektAllTypes> reflektAllTypes) {
        this.conf = conf;
        this.reflektAllTypes = reflektAllTypes;
    }

    @Override
    public Set<Class> getAllClasses() {
        return keeper.get();
    }

    private Set<Class> init() {
        Set<String> allTypes = reflektAllTypes.get().getAllTypes();
        if(classLoader == null){
            if (conf.getClassResourceDirs() != null) {
                URL[] urls = conf.getClassResourceDirs().stream().map(File::new).map(File::toURI).map(this::toUrl).toArray(URL[]::new);
                classLoader = new URLClassLoader(urls);
            } else {
                classLoader = Thread.currentThread().getContextClassLoader();
            }
        }
        if (allTypes.size() > PARALLEL_CLASS_LOAD_BREAKPOINT) {
            return allTypes.stream().parallel()
                    .map(this::safeLoad)
                    .filter(Objects::nonNull)
                    .collect(toSet());
        } else {
            return allTypes.stream()
                    .map(this::safeLoad)
                    .filter(Objects::nonNull)
                    .collect(toSet());
        }
    }

    private URL toUrl(URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private Class safeLoad(String classRef) {
        try {
            return classLoader.loadClass(classRef);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return null;
        }
    }
}
