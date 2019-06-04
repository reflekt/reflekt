package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektMethodsWithAnyParamAnnotated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

class ReflektMethodsWithAnyParamAnnotatedImpl implements ReflektMethodsWithAnyParamAnnotated {
    @Override
    public Set<Method> getMethodsWithAnyParamAnnotated(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
