package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektConstructorsWithAnyParamAnnotated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

class ReflektConstructorsWithAnyParamAnnotatedImpl implements ReflektConstructorsWithAnyParamAnnotated {
    @Override
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
