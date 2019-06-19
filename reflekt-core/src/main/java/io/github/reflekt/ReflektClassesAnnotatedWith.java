package io.github.reflekt;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface ReflektClassesAnnotatedWith {

    /**
     * Only Runtime-visible annotations are discoverable
     * @see java.lang.annotation.Retention
     * @param annotation the annotation you are scanning for
     * @return all classes annotated with the annotation
     */
    Set<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation);
}
