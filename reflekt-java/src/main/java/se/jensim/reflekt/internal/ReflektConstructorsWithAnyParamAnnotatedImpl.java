package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektConstructorsWithAnyParamAnnotated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

class ReflektConstructorsWithAnyParamAnnotatedImpl extends ReflektAbstractAnyParamAnnotated<Constructor> implements ReflektConstructorsWithAnyParamAnnotated {

    private final ReflektAllConstructors reflektAllConstructors;

    ReflektConstructorsWithAnyParamAnnotatedImpl(ReflektAllConstructors reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Constructor> getSourceDatas() {
        return reflektAllConstructors.getAllConstructors();
    }

    @Override
    protected Annotation[][] getAnnotationsFromParams(Constructor type) {
        return type.getParameterAnnotations();
    }
}
