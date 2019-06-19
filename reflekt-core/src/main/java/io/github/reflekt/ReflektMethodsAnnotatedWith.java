package io.github.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsAnnotatedWith {

    /**
     * Only Runtime-visible annotations are discoverable
     * @param annotation the annotation you are scanning for
     * @return all methods annotated with the annotation
     * @see java.lang.annotation.Retention
     */
    Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation);
}
