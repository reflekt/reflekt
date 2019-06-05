package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllFields;

import java.lang.reflect.Field;
import java.util.Set;

public class ReflektAllFieldsImpl implements ReflektAllFields {

    private final ReflektAllClasses reflektAllClasses;

     ReflektAllFieldsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Field> getAllFields() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
