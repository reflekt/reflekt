package se.jensim.reflekt.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Supplier;

import se.jensim.reflekt.ReflektAllFields;
import se.jensim.reflekt.ReflektFieldsAnnotatedWith;

class ReflektFieldsAnnotatedWithImpl extends ReflektAbstractAnnotatedWith<Field> implements ReflektFieldsAnnotatedWith {

    private final Supplier<ReflektAllFields> reflektAllFields;

    ReflektFieldsAnnotatedWithImpl(Supplier<ReflektAllFields> reflektAllFields) {
        this.reflektAllFields = reflektAllFields;
    }

    @Override
    public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Field> getSourceDatas() {
        return reflektAllFields.get().getAllFields();
    }
}
