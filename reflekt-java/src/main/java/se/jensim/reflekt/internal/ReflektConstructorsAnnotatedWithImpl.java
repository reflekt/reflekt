package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektConstructorsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class ReflektConstructorsAnnotatedWithImpl implements ReflektConstructorsAnnotatedWith {

    private final Map<Boolean, Map<String, Set<Constructor>>> keeper = new ConcurrentHashMap<>();
    private final Set<Constructor> defaultValue = Collections.emptySet();
    private final ReflektAllConstructors reflektAllConstructors;

    ReflektConstructorsAnnotatedWithImpl(ReflektAllConstructors reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsAnnotatedWith(Class<Annotation> annotation) {
        return keeper.computeIfAbsent(false,b -> init())
                .getOrDefault(annotation.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<Constructor>> init() {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO
    }
}
