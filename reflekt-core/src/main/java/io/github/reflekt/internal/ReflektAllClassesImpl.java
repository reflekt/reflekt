package io.github.reflekt.internal;

import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.function.Supplier;

import io.github.reflekt.ReflektAllTypes;

class ReflektAllClassesImpl implements ReflektAllClasses {

    private static final int PARALLEL_CLASS_LOAD_BREAKPOINT = 49;
    private final Supplier<Set<Class>> keeper = LazyBuilder.lazy(this::init);
    private final Supplier<ReflektAllTypes> reflektAllTypes;

    ReflektAllClassesImpl(Supplier<ReflektAllTypes> reflektAllTypes) {
        this.reflektAllTypes = reflektAllTypes;
    }

    @Override
    public Set<Class> getAllClasses() {
        return keeper.get();
    }

    private Set<Class> init() {
        var allTypes = reflektAllTypes.get().getAllTypes();
        if (allTypes.size() > PARALLEL_CLASS_LOAD_BREAKPOINT) {
            return allTypes.stream().parallel().map(this::safeLoad).collect(toSet());
        } else {
            return allTypes.stream().map(this::safeLoad).collect(toSet());
        }
    }

    private Class safeLoad(String classRef) {
        try {
            return Class.forName(classRef);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return null;
        }
    }
}
