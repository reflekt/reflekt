package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektConstructorsWithAnyParamAnnotated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class ReflektConstructorsWithAnyParamAnnotatedImpl implements ReflektConstructorsWithAnyParamAnnotated {

    private final Map<Boolean, Map<String, Set<Constructor>>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllConstructors reflektAllConstructors;
    private Set<Constructor> defaultValue = Collections.emptySet();

    ReflektConstructorsWithAnyParamAnnotatedImpl(ReflektAllConstructors reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return keeper.computeIfAbsent(false, b -> init()).getOrDefault(annotation.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<Constructor>> init() {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO
}
}
