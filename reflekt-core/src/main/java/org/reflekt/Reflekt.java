package org.reflekt;

public interface Reflekt extends
        ReflektAllConstructors,
        ReflektAllFields,
        ReflektAllMethods,
        ReflektAllTypes,
        ReflektClassesAnnotatedWith,
        ReflektConstructorsAnnotatedWith,
        ReflektConstructorsMatchParams,
        ReflektConstructorsWithAnyParamAnnotated,
        ReflektFieldsAnnotatedWith,
        ReflektMethodsAnnotatedWith,
        ReflektMethodsMatchParams,
        ReflektMethodsReturn,
        ReflektMethodsWithAnyParamAnnotated,
        ReflektSubClasses {

    // Wont implement at this time.

    //fun getResources(predicate: Predicate<String>): Set<String>

    // fun getMethodParamNames(method: Method): List<String>
    // fun getConstructorParamNames(constructor: Constructor<*>): List<String>

    // fun getFieldUsage(field: Field): Set<Member>
    // fun getMethodUsage(method: Method): Set<Member>
    // fun getConstructorUsage(constructor: Constructor<*>): Set<Member>
}
