package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektFieldsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

class ReflektFieldsAnnotatedWithImpl implements ReflektFieldsAnnotatedWith {
    @Override
    public Set<Field> getFieldsAnnotatedWith(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
