package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektConstructorsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

class ReflektConstructorsAnnotatedWithImpl implements ReflektConstructorsAnnotatedWith {
    @Override
    public Set<Constructor> getConstructorsAnnotatedWith(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
