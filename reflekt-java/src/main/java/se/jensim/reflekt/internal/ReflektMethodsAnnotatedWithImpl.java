package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektMethodsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class ReflektMethodsAnnotatedWithImpl implements ReflektMethodsAnnotatedWith {

    private final Map<Boolean, Map<String, Set<Method>>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllMethods reflektAllMethods;
    private Set<Method> defaultValue = Collections.emptySet();

    ReflektMethodsAnnotatedWithImpl(ReflektAllMethods reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsAnnotatedWith(Class<Annotation> annotation) {
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(annotation.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<Method>> init() {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO
}
}
