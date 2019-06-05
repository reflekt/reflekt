package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllTypes;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class ReflektAllClassesImpl implements ReflektAllClasses {

    private static final int PARALLEL_CLASS_LOAD_BREAKPOINT = 49;
    private final Map<Boolean, Set<Class>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllTypes reflektAllTypes;

    ReflektAllClassesImpl(ReflektAllTypes reflektAllTypes) {
        this.reflektAllTypes = reflektAllTypes;
    }

    @Override
    public Set<Class> getAllClasses() {
        return keeper.computeIfAbsent(false, b -> init());
    }

    private Set<Class> init() {
        var allTypes = reflektAllTypes.getAllTypes();
        if (allTypes.size() > PARALLEL_CLASS_LOAD_BREAKPOINT) {
            return allTypes.stream().parallel().map(this::safeLoad).collect(Collectors.toSet());
        } else {
            return allTypes.stream().map(this::safeLoad).collect(Collectors.toSet());
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
