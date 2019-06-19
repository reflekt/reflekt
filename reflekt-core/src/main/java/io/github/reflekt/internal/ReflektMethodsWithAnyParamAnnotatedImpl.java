package io.github.reflekt.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Supplier;

import io.github.reflekt.ReflektAllMethods;
import io.github.reflekt.ReflektMethodsWithAnyParamAnnotated;

class ReflektMethodsWithAnyParamAnnotatedImpl extends ReflektAbstractAnyParamAnnotated<Method> implements ReflektMethodsWithAnyParamAnnotated {

    private final Supplier<ReflektAllMethods> reflektAllMethods;

    ReflektMethodsWithAnyParamAnnotatedImpl(Supplier<ReflektAllMethods> reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Method> getSourceDatas() {
        return reflektAllMethods.get().getAllMethods();
    }

    @Override
    protected Annotation[][] getAnnotationsFromParams(Method type) {
        return type.getParameterAnnotations();
    }
}
