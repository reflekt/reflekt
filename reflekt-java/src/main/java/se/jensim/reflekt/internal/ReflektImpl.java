package se.jensim.reflekt.internal;

import se.jensim.reflekt.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

class ReflektImpl implements Reflekt {

    private final ReflektAllTypes reflektAllTypes;
    private final ReflektClassesAnnotatedWith reflektClassesAnnotatedWith;
    private final ReflektConstructorsAnnotatedWith reflektConstructorsAnnotatedWith;
    private final ReflektConstructorsMatchParams reflektConstructorsMatchParams;
    private final ReflektConstructorsWithAnyParamAnnotated reflektConstructorsWithAnyParamAnnotated;
    private final ReflektFieldsAnnotatedWith reflektFieldsAnnotatedWith;
    private final ReflektMethodsAnnotatedWith reflektMethodsAnnotatedWith;
    private final ReflektMethodsMatchParams reflektMethodsMatchParams;
    private final ReflektMethodsReturn reflektMethodsReturn;
    private final ReflektMethodsWithAnyParamAnnotated reflektMethodsWithAnyParamAnnotated;
    private final ReflektSubClasses reflektSubClasses;

    ReflektImpl(
            ReflektAllTypes reflektAllTypes,
            ReflektClassesAnnotatedWith reflektClassesAnnotatedWith,
            ReflektConstructorsAnnotatedWith reflektConstructorsAnnotatedWith,
            ReflektConstructorsMatchParams reflektConstructorsMatchParams,
            ReflektConstructorsWithAnyParamAnnotated reflektConstructorsWithAnyParamAnnotated,
            ReflektFieldsAnnotatedWith reflektFieldsAnnotatedWith,
            ReflektMethodsAnnotatedWith reflektMethodsAnnotatedWith,
            ReflektMethodsMatchParams reflektMethodsMatchParams,
            ReflektMethodsReturn reflektMethodsReturn,
            ReflektMethodsWithAnyParamAnnotated reflektMethodsWithAnyParamAnnotated,
            ReflektSubClasses reflektSubClasses) {

        this.reflektAllTypes = reflektAllTypes;
        this.reflektClassesAnnotatedWith = reflektClassesAnnotatedWith;
        this.reflektConstructorsAnnotatedWith = reflektConstructorsAnnotatedWith;
        this.reflektConstructorsMatchParams = reflektConstructorsMatchParams;
        this.reflektConstructorsWithAnyParamAnnotated = reflektConstructorsWithAnyParamAnnotated;
        this.reflektFieldsAnnotatedWith = reflektFieldsAnnotatedWith;
        this.reflektMethodsAnnotatedWith = reflektMethodsAnnotatedWith;
        this.reflektMethodsMatchParams = reflektMethodsMatchParams;
        this.reflektMethodsReturn = reflektMethodsReturn;
        this.reflektMethodsWithAnyParamAnnotated = reflektMethodsWithAnyParamAnnotated;
        this.reflektSubClasses = reflektSubClasses;
    }

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
        return reflektConstructorsAnnotatedWith.getConstructorsAnnotatedWith(annotation);
    }

    @Override
    public Set<Constructor> getConstructorsMatchParams(Class... paramClasses) {
        return reflektConstructorsMatchParams.getConstructorsMatchParams(paramClasses);
    }

    @Override
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<Annotation> annotation) {
        return reflektConstructorsWithAnyParamAnnotated.getConstructorsWithAnyParamAnnotated(annotation);
    }

    @Override
    public Set<Field> getFieldsAnnotatedWith(Class<Annotation> annotation) {
        return reflektFieldsAnnotatedWith.getFieldsAnnotatedWith(annotation);
    }

    @Override
    public Set<Method> getMethodsAnnotatedWith(Class<Annotation> annotation) {
        return reflektMethodsAnnotatedWith.getMethodsAnnotatedWith(annotation);
    }

    @Override
    public Set<Method> getMethodsMatchParams(Class... paramClasses) {
        return reflektMethodsMatchParams.getMethodsMatchParams(paramClasses);
    }

    @Override
    public Set<Method> getMethodsReturn(Class clazz) {
        return reflektMethodsReturn.getMethodsReturn(clazz);
    }

    @Override
    public Set<Method> getMethodsWithAnyParamAnnotated(Class<Annotation> annotation) {
        return reflektMethodsWithAnyParamAnnotated.getMethodsWithAnyParamAnnotated(annotation);
    }

    @Override
    public Set<Class> getSubClasses(Class clazz) {
        return reflektSubClasses.getSubClasses(clazz);
    }
}
