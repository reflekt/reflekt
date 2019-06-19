package org.reflekt.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

abstract class ReflektAbstractAnnotatedWith<T extends AnnotatedElement> {

    private final Map<String, Set<T>> keeper = new ConcurrentHashMap<>();

    protected abstract Set<T> getSourceDatas();

    protected Set<T> getAnnotatedTypes(Class<? extends Annotation> annotation) {
        return keeper.computeIfAbsent(annotation.getCanonicalName(), a -> initAnnotatedTypes(annotation));
    }

    protected Set<T> initAnnotatedTypes(Class<? extends Annotation> annotation) {
        return getSourceDatas().stream().filter(t -> t.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }
}
