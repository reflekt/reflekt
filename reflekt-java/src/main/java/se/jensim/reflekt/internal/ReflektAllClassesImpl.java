package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllTypes;

import java.util.Set;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toSet;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

class ReflektAllClassesImpl implements ReflektAllClasses {

    private static final int PARALLEL_CLASS_LOAD_BREAKPOINT = 49;
    private final Supplier<Set<Class>> keeper = lazy(this::init);
    private final ReflektAllTypes reflektAllTypes;

    ReflektAllClassesImpl(ReflektAllTypes reflektAllTypes) {
        this.reflektAllTypes = reflektAllTypes;
    }

    @Override
    public Set<Class> getAllClasses() {
        return keeper.get();
    }

    private Set<Class> init() {
        var allTypes = reflektAllTypes.getAllTypes();
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
