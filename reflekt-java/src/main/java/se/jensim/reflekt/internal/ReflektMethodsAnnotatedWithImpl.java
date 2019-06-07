package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektMethodsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

class ReflektMethodsAnnotatedWithImpl extends ReflektAbstractAnnotatedWith<Method> implements ReflektMethodsAnnotatedWith {

    private final ReflektAllMethods reflektAllMethods;

    ReflektMethodsAnnotatedWithImpl(ReflektAllMethods reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Method> getSourceDatas() {
        return reflektAllMethods.getAllMethods();
    }
}
