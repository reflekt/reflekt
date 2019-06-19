package io.github.reflekt.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.function.Supplier;

import io.github.reflekt.ReflektAllConstructors;
import io.github.reflekt.ReflektConstructorsWithAnyParamAnnotated;

class ReflektConstructorsWithAnyParamAnnotatedImpl extends ReflektAbstractAnyParamAnnotated<Constructor> implements ReflektConstructorsWithAnyParamAnnotated {

    private final Supplier<ReflektAllConstructors> reflektAllConstructors;

    ReflektConstructorsWithAnyParamAnnotatedImpl(Supplier<ReflektAllConstructors> reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Constructor> getSourceDatas() {
        return reflektAllConstructors.get().getAllConstructors();
    }

    @Override
    protected Annotation[][] getAnnotationsFromParams(Constructor type) {
        return type.getParameterAnnotations();
    }
}
