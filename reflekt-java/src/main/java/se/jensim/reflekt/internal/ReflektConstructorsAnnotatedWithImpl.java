package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektConstructorsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

class ReflektConstructorsAnnotatedWithImpl extends ReflektAbstractAnnotatedWith<Constructor> implements ReflektConstructorsAnnotatedWith {

    private final ReflektAllConstructors reflektAllConstructors;

    ReflektConstructorsAnnotatedWithImpl(ReflektAllConstructors reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsAnnotatedWith(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Constructor> getSourceDatas() {
        return reflektAllConstructors.getAllConstructors();
    }
}
