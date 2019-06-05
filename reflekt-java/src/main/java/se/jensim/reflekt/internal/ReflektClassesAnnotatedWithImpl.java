package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektClassesAnnotatedWith;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

class ReflektClassesAnnotatedWithImpl implements ReflektClassesAnnotatedWith {

    private final Map<Boolean, Map<String, Set<Class>>> keeper = new ConcurrentHashMap<>();
    private final Set<Class> defaultReturn = Collections.emptySet();
    private final ReflektAllClasses reflektAllClasses;

    ReflektClassesAnnotatedWithImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(annotation.getCanonicalName(), defaultReturn);
    }

    private Map<String, Set<Class>> init() {
        return reflektAllClasses.getAllClasses().stream()
                .flatMap(this::multiplex)
                .collect(groupingBy(h -> h.getAnnotation().annotationType().getCanonicalName(),
                        mapping(Holder::getAnnotatedClass, toSet())));
    }

    private Stream<Holder> multiplex(Class clazz) {
        return Arrays.stream(clazz.getAnnotations()).map(a -> new Holder(a, clazz));
    }

    private class Holder {

        private final Annotation annotation;
        private final Class annotatedClass;

        private Holder(Annotation annotation, Class annotatedClass) {
            this.annotation = annotation;
            this.annotatedClass = annotatedClass;
        }

        private Annotation getAnnotation() {
            return annotation;
        }

        private Class getAnnotatedClass() {
            return annotatedClass;
        }
    }
}
