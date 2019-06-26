package io.github.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

public interface ReflektFieldsAnnotatedWith {

    /**
     * Only Runtime-visible annotations are discoverable
     * @param annotation the annotation you are scanning for
     * @return all fields annotated with the annotation
     * @see java.lang.annotation.Retention
     */
    Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation);
}
