package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektMethodsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

class ReflektMethodsAnnotatedWithImpl implements ReflektMethodsAnnotatedWith {
    @Override
    public Set<Method> getMethodsAnnotatedWith(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
