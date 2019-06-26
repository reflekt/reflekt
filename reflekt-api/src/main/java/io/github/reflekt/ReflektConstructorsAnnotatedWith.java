package io.github.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

public interface ReflektConstructorsAnnotatedWith {

    /**
     * Only Runtime-visible annotations are discoverable
     * @see java.lang.annotation.Retention
     * @return all constructors annotated with the annotation
     * @param annotation the annotation you are scanning for
     */
    Set<Constructor> getConstructorsAnnotatedWith(Class<? extends Annotation> annotation);
}
