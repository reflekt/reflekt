package se.jensim.reflekt.internal;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

abstract class ReflektAbstractAnyParamAnnotated<T> {

    private final Map<Boolean, Map<String, Set<T>>> keeper = new ConcurrentHashMap<>();
    private Set<T> defaultValue = Collections.emptySet();

    protected Set<T> getAnnotatedTypes(Class<? extends Annotation> annotation) {
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(annotation.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<T>> init() {
        return getSourceDatas().stream()
                .flatMap(this::multiplex)
                .collect(groupingBy(p -> p.annotation.annotationType().getCanonicalName(),
                        mapping(h -> h.type, toSet())));
    }

    protected abstract Set<T> getSourceDatas();

    private Stream<Pair> multiplex(T type) {
        return getAnnotationSetFromParams(type).stream().map(a -> new Pair(type, a));
    }

    private Set<Annotation> getAnnotationSetFromParams(T type) {
        return Arrays.stream(getAnnotationsFromParams(type)).flatMap(Arrays::stream).collect(Collectors.toSet());
    }

    protected abstract Annotation[][] getAnnotationsFromParams(T type);

    private class Pair {

        private final T type;
        private final Annotation annotation;

        private Pair(T type, Annotation annotation) {
            this.type = type;
            this.annotation = annotation;
        }
    }
}
