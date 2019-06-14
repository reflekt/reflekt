package se.jensim.reflekt.internal;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

abstract class ReflektAbstractAnyParamAnnotated<T> {

    private final Supplier<Map<String, Set<T>>> keeper = lazy(this::init);
    private Set<T> defaultValue = Collections.emptySet();

    protected Set<T> getAnnotatedTypes(Class<? extends Annotation> annotation) {
        return keeper.get().getOrDefault(annotation.getCanonicalName(), defaultValue);
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
        return Arrays.stream(getAnnotationsFromParams(type)).flatMap(Arrays::stream).collect(toSet());
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
