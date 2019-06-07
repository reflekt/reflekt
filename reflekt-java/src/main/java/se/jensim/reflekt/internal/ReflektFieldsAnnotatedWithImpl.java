package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllFields;
import se.jensim.reflekt.ReflektFieldsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

class ReflektFieldsAnnotatedWithImpl extends ReflektAbstractAnnotatedWith<Field> implements ReflektFieldsAnnotatedWith {

    private final ReflektAllFields reflektAllFields;

    ReflektFieldsAnnotatedWithImpl(ReflektAllFields reflektAllFields) {
        this.reflektAllFields = reflektAllFields;
    }

    @Override
    public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Field> getSourceDatas() {
        return reflektAllFields.getAllFields();
    }
}
