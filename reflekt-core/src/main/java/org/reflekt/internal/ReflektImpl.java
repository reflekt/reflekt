package org.reflekt.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.reflekt.Reflekt;
import org.reflekt.ReflektAllConstructors;
import org.reflekt.ReflektAllFields;
import org.reflekt.ReflektAllMethods;
import org.reflekt.ReflektAllTypes;
import org.reflekt.ReflektClassesAnnotatedWith;
import org.reflekt.ReflektConstructorsAnnotatedWith;
import org.reflekt.ReflektConstructorsMatchParams;
import org.reflekt.ReflektConstructorsWithAnyParamAnnotated;
import org.reflekt.ReflektFieldsAnnotatedWith;
import org.reflekt.ReflektMethodsAnnotatedWith;
import org.reflekt.ReflektMethodsMatchParams;
import org.reflekt.ReflektMethodsReturn;
import org.reflekt.ReflektMethodsWithAnyParamAnnotated;
import org.reflekt.ReflektSubClasses;

class ReflektImpl implements Reflekt {

    private final Supplier<ReflektAllConstructors> reflektAllConstructors;
    private final Supplier<ReflektAllFields> reflektAllFields;
    private final Supplier<ReflektAllMethods> reflektAllMethods;
    private final Supplier<ReflektAllTypes> reflektAllTypes;
    private final Supplier<ReflektClassesAnnotatedWith> reflektClassesAnnotatedWith;
    private final Supplier<ReflektConstructorsAnnotatedWith> reflektConstructorsAnnotatedWith;
    private final Supplier<ReflektConstructorsMatchParams> reflektConstructorsMatchParams;
    private final Supplier<ReflektConstructorsWithAnyParamAnnotated> reflektConstructorsWithAnyParamAnnotated;
    private final Supplier<ReflektFieldsAnnotatedWith> reflektFieldsAnnotatedWith;
    private final Supplier<ReflektMethodsAnnotatedWith> reflektMethodsAnnotatedWith;
    private final Supplier<ReflektMethodsMatchParams> reflektMethodsMatchParams;
    private final Supplier<ReflektMethodsReturn> reflektMethodsReturn;
    private final Supplier<ReflektMethodsWithAnyParamAnnotated> reflektMethodsWithAnyParamAnnotated;
    private final Supplier<ReflektSubClasses> reflektSubClasses;

    ReflektImpl(
            Supplier<ReflektAllConstructors> reflektAllConstructors,
            Supplier<ReflektAllFields> reflektAllFields,
            Supplier<ReflektAllMethods> reflektAllMethods,
            Supplier<ReflektAllTypes> reflektAllTypes,
            Supplier<ReflektClassesAnnotatedWith> reflektClassesAnnotatedWith,
            Supplier<ReflektConstructorsAnnotatedWith> reflektConstructorsAnnotatedWith,
            Supplier<ReflektConstructorsMatchParams> reflektConstructorsMatchParams,
            Supplier<ReflektConstructorsWithAnyParamAnnotated> reflektConstructorsWithAnyParamAnnotated,
            Supplier<ReflektFieldsAnnotatedWith> reflektFieldsAnnotatedWith,
            Supplier<ReflektMethodsAnnotatedWith> reflektMethodsAnnotatedWith,
            Supplier<ReflektMethodsMatchParams> reflektMethodsMatchParams,
            Supplier<ReflektMethodsReturn> reflektMethodsReturn,
            Supplier<ReflektMethodsWithAnyParamAnnotated> reflektMethodsWithAnyParamAnnotated,
            Supplier<ReflektSubClasses> reflektSubClasses) {

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
        return reflektAllConstructors.get().getAllConstructors();
    }

    @Override
    public Set<Field> getAllFields() {
        return reflektAllFields.get().getAllFields();
    }

    @Override
    public Set<Method> getAllMethods() {
        return reflektAllMethods.get().getAllMethods();
    }

    @Override
    public Set<String> getAllTypes() {
        return reflektAllTypes.get().getAllTypes();
    }

    @Override
    public Set<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektClassesAnnotatedWith.get().getClassesAnnotatedWith(annotation);
    }

    @Override
    public Set<Constructor> getConstructorsAnnotatedWith(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektConstructorsAnnotatedWith.get().getConstructorsAnnotatedWith(annotation);
    }

    @Override
    public Set<Constructor> getConstructorsMatchParams(Class... paramClasses) {
        Objects.requireNonNull(paramClasses);
        return reflektConstructorsMatchParams.get().getConstructorsMatchParams(paramClasses);
    }

    @Override
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektConstructorsWithAnyParamAnnotated.get().getConstructorsWithAnyParamAnnotated(annotation);
    }

    @Override
    public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektFieldsAnnotatedWith.get().getFieldsAnnotatedWith(annotation);
    }

    @Override
    public Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektMethodsAnnotatedWith.get().getMethodsAnnotatedWith(annotation);
    }

    @Override
    public Set<Method> getMethodsMatchParams(Class... paramClasses) {
        Objects.requireNonNull(paramClasses);
        return reflektMethodsMatchParams.get().getMethodsMatchParams(paramClasses);
    }

    @Override
    public Set<Method> getMethodsReturn(Class clazz) {
        Objects.requireNonNull(clazz);
        return reflektMethodsReturn.get().getMethodsReturn(clazz);
    }

    @Override
    public Set<Method> getMethodsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        Objects.requireNonNull(annotation);
        return reflektMethodsWithAnyParamAnnotated.get().getMethodsWithAnyParamAnnotated(annotation);
    }

    @Override
    public Set<Class> getSubClasses(Class clazz) {
        Objects.requireNonNull(clazz);
        return reflektSubClasses.get().getSubClasses(clazz);
    }
}
