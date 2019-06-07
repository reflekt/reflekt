package se.jensim.reflekt.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

abstract class ReflektAbstractAnnotatedWith<T extends AnnotatedElement> {

    private final Supplier<Map<String, Set<T>>> keeper = lazy(this::getMapOfAnnotionToData);
    private final Set<T> defaultValue = Collections.emptySet();

    protected abstract Set<T> getSourceDatas();

    protected Set<T> getAnnotatedTypes(Class<? extends Annotation> annotation) {
        return keeper.get().getOrDefault(annotation.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<T>> getMapOfAnnotionToData() {
        return getSourceDatas().stream()
                .flatMap(this::multiplex)
                .collect(groupingBy(p -> p.annotation.annotationType().getCanonicalName(),
                        mapping(h -> h.data, toSet())));
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
