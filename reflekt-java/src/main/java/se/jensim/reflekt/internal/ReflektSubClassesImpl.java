package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektSubClasses;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class ReflektSubClassesImpl implements ReflektSubClasses {

    private final Map<Boolean, Map<String, Set<Class>>> keeper = new ConcurrentHashMap<>();
    private final Set<Class> defaultValue = Collections.emptySet();
    private final ReflektAllClasses reflektAllClasses;

    ReflektSubClassesImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Class> getSubClasses(Class clazz) {
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(clazz.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<Class>> init() {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO
    }
}
