package se.jensim.reflekt.internal;

import se.jensim.reflekt.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

class ReflektImpl implements Reflekt {

    private final ReflektAllConstructors reflektAllConstructors;
    private final ReflektAllFields reflektAllFields;
    private final ReflektAllMethods reflektAllMethods;
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
            ReflektAllConstructors reflektAllConstructors,
            ReflektAllFields reflektAllFields,
            ReflektAllMethods reflektAllMethods,
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

        this.reflektAllConstructors = reflektAllConstructors;
        this.reflektAllFields = reflektAllFields;
        this.reflektAllMethods = reflektAllMethods;
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
    public Set<Constructor> getAllConstructors() {
        return reflektAllConstructors.getAllConstructors();
    }

    @Override
    public Set<Field> getAllFields() {
        return reflektAllFields.getAllFields();
    }

    @Override
    public Set<Method> getAllMethods() {
        return reflektAllMethods.getAllMethods();
    }

    @Override
    public Set<String> getAllTypes() {
        return reflektAllTypes.getAllTypes();
    }

    @Override
    public Set<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektClassesAnnotatedWith.getClassesAnnotatedWith(annotation);
    }

    @Override
    public Set<Constructor> getConstructorsAnnotatedWith(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektConstructorsAnnotatedWith.getConstructorsAnnotatedWith(annotation);
    }

    @Override
    public Set<Constructor> getConstructorsMatchParams(Class... paramClasses) {
        Objects.requireNonNull(paramClasses);
        return reflektConstructorsMatchParams.getConstructorsMatchParams(paramClasses);
    }

    @Override
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektConstructorsWithAnyParamAnnotated.getConstructorsWithAnyParamAnnotated(annotation);
    }

    @Override
    public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektFieldsAnnotatedWith.getFieldsAnnotatedWith(annotation);
    }

    @Override
    public Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektMethodsAnnotatedWith.getMethodsAnnotatedWith(annotation);
    }

    @Override
    public Set<Method> getMethodsMatchParams(Class... paramClasses) {
        Objects.requireNonNull(paramClasses);
        return reflektMethodsMatchParams.getMethodsMatchParams(paramClasses);
    }

    @Override
    public Set<Method> getMethodsReturn(Class clazz) {
        Objects.requireNonNull(clazz);
        return reflektMethodsReturn.getMethodsReturn(clazz);
    }

    @Override
    public Set<Method> getMethodsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektMethodsWithAnyParamAnnotated.getMethodsWithAnyParamAnnotated(annotation);
    }

    @Override
    public Set<Class> getSubClasses(Class clazz) {
        Objects.requireNonNull(clazz);
        return reflektSubClasses.getSubClasses(clazz);
    }
}
