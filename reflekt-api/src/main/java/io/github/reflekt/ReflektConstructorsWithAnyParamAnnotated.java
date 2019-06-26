package io.github.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

public interface ReflektConstructorsWithAnyParamAnnotated {

    /**
     * Only Runtime-visible annotations are discoverable
     * @param annotation the annotation you are scanning for
     * @return all constructors with any param annotated with the annotation
     * @see java.lang.annotation.Retention
     */
    Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation);
}
