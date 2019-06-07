package se.jensim.reflekt.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

abstract class ReflektAbstractAnnotatedWith<T extends AnnotatedElement> {

    private final Map<Boolean, Map<String, Set<T>>> keeper = new ConcurrentHashMap<>();
    private final Set<T> defaultValue = Collections.emptySet();

    protected abstract Set<T> getSourceDatas();

    protected Set<T> getAnnotatedTypes(Class<? extends Annotation> annotation) {
        return keeper.computeIfAbsent(false, b -> getMapOfAnnotionToData())
                .getOrDefault(annotation.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<T>> getMapOfAnnotionToData() {
        return getSourceDatas().stream()
                .flatMap(this::multiplex)
                .collect(Collectors.groupingBy((Pair p) -> p.annotation.annotationType().getCanonicalName(),
                        Collectors.mapping((Pair h) -> h.data, toSet())));
    }

    private Stream<Pair> multiplex(T data) {
        return Arrays.stream(data.getAnnotations()).map(a -> new Pair(a, data));
    }

    private class Pair {

        final Annotation annotation;
        final T data;

        private Pair(Annotation annotation, T data) {
            this.annotation = Objects.requireNonNull(annotation);
            this.data = Objects.requireNonNull(data);
        }
    }
}
