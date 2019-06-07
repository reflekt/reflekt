package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektMethodsWithAnyParamAnnotated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

class ReflektMethodsWithAnyParamAnnotatedImpl extends ReflektAbstractAnyParamAnnotated<Method> implements ReflektMethodsWithAnyParamAnnotated {

    private final ReflektAllMethods reflektAllMethods;

    ReflektMethodsWithAnyParamAnnotatedImpl(ReflektAllMethods reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Method> getSourceDatas() {
        return reflektAllMethods.getAllMethods();
    }

    @Override
    protected Annotation[][] getAnnotationsFromParams(Method type) {
        return type.getParameterAnnotations();
    }
}
