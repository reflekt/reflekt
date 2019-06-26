package io.github.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsWithAnyParamAnnotated {

    /**
     * Only Runtime-visible annotations are discoverable
     * @param annotation the annotation you are scanning for
     * @return all methods with any param annotated with the annotation
     * @see java.lang.annotation.Retention
     */
    Set<Method> getMethodsWithAnyParamAnnotated(Class<? extends Annotation> annotation);
}
