package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektClassesAnnotatedWith;

import java.lang.annotation.Annotation;
import java.util.Set;

class ReflektClassesAnnotatedWithImpl extends ReflektAbstractAnnotatedWith<Class> implements ReflektClassesAnnotatedWith {

    private final ReflektAllClasses reflektAllClasses;

    ReflektClassesAnnotatedWithImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Class> getSourceDatas() {
        return reflektAllClasses.getAllClasses();
    }
}
