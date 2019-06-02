package se.jensim.reflekt

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Member
import java.lang.reflect.Method
import java.util.function.Predicate

interface RefleKt {

    fun getClassesAnnotatedWith(annotation: Class<out Annotation>): Set<Class<*>>
    fun getSubClasses(clazz: Class<*>): Set<Class<*>>
    fun getMethodsAnnotatedWith(annotation: Class<out Annotation>): Set<Method>
    fun getMethodsMatchParams(vararg paramClasses: Class<*>): Set<Method>
    fun getMethodsReturn(clazz: Class<*>): Set<Method>
    fun getMethodsWithAnyParamAnnotated(annotation: Class<out Annotation>): Set<Method>
    fun getConstructorsAnnotatedWith(annotation: Class<out Annotation>): Set<Constructor<*>>
    fun getConstructorsMatchParams(vararg paramClasses: Class<*>): Set<Constructor<*>>
    fun getConstructorsWithAnyParamAnnotated(annotation: Class<out Annotation>): Set<Constructor<*>>
    fun getFieldsAnnotatedWith(annotation: Class<out Annotation>): Set<Field>
    fun getResources(predicate: Predicate<String>): Set<String>
    fun getMethodParamNames(method: Method): List<String>
    fun getConstructorParamNames(constructor: Constructor<*>): List<String>
    fun getFieldUsage(field: Field): Set<Member>
    fun getMethodUsage(method: Method): Set<Member>
    fun getConstructorUsage(constructor: Constructor<*>): Set<Member>
    fun getAllTypes(): Set<String>
}
