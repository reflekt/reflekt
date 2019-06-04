package se.jensim.reflekt.internal;

import se.jensim.reflekt.Reflekt;
import se.jensim.reflekt.ReflektAllTypes;
import se.jensim.reflekt.ReflektClassesAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

class ReflektImpl implements Reflekt {

    private final ReflektAllTypes reflektAllTypes = new ReflektAllTypesImpl();
    private final ReflektClassesAnnotatedWith reflektClassesAnnotatedWith = new ReflektClassesAnnotatedWithImpl();

    @Override
    public Set<String> getAllTypes() {
        return reflektAllTypes.getAllTypes();
    }

    @Override
    public Set<Class> getClassesAnnotatedWith(Class<Annotation> annotation) {
        return reflektClassesAnnotatedWith.getClassesAnnotatedWith(annotation);
    }

    @Override
    public Set<Constructor> getConstructorsAnnotatedWith(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<Constructor> getConstructorsMatchParams(Class... paramClasses) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<Field> getFieldsAnnotatedWith(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<Method> getMethodsAnnotatedWith(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<Method> getMethodsMatchParams(Class... paramClasses) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<Method> getMethodsReturn(Class clazz) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<Method> getMethodsWithAnyParamAnnotated(Class<Annotation> annotation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Set<Class> getSubClasses(Class clazz) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
