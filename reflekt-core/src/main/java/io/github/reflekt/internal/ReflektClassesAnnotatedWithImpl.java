package io.github.reflekt.internal;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.Supplier;

import io.github.reflekt.ReflektClassesAnnotatedWith;

class ReflektClassesAnnotatedWithImpl extends ReflektAbstractAnnotatedWith<Class> implements ReflektClassesAnnotatedWith {

    private final Supplier<ReflektAllClasses> reflektAllClasses;

    ReflektClassesAnnotatedWithImpl(Supplier<ReflektAllClasses> reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        return getAnnotatedTypes(annotation);
    }

    @Override
    protected Set<Class> getSourceDatas() {
        return reflektAllClasses.get().getAllClasses();
    }
}
