package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektClassesAnnotatedWith;

import java.lang.annotation.Annotation;
import java.util.Set;

class ReflektClassesAnnotatedWithImpl implements ReflektClassesAnnotatedWith {

    @Override
    public Set<Class> getClassesAnnotatedWith(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
