package se.jensim.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

public interface ReflektFieldsAnnotatedWith {

    Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation);
}
