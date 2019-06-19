package io.github.reflekt.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.function.Supplier;

import io.github.reflekt.ReflektAllConstructors;
import io.github.reflekt.ReflektConstructorsAnnotatedWith;

class ReflektConstructorsAnnotatedWithImpl extends ReflektAbstractAnnotatedWith<Constructor> implements ReflektConstructorsAnnotatedWith {

    private final Supplier<ReflektAllConstructors> reflektAllConstructors;

    ReflektConstructorsAnnotatedWithImpl(Supplier<ReflektAllConstructors> reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsAnnotatedWith(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Constructor> getSourceDatas() {
        return reflektAllConstructors.get().getAllConstructors();
    }
}
