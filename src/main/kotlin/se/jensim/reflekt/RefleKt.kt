package se.jensim.reflekt

import se.jensim.reflekt.internal.ReflektStore.getClasses
import se.jensim.reflekt.internal.getClassFileLocators
import se.jensim.reflekt.internal.packageFilter
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Member
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Predicate

class RefleKt(conf: RefleKtConf) {

    private val classFileLocators = conf.getClassFileLocators()
    private val packageFilter = conf.packageFilter()

    constructor(confDsl: RefleKtConf.() -> Unit) : this(RefleKtConf().apply(confDsl))
    constructor() : this(RefleKtConf())

    private val allClasses by lazy { classFileLocators.flatMap { it.getClasses(conf.classFileLocatorConf.includeNestedJars) } }
    private val filteredClasses by lazy { allClasses.filter(packageFilter).toSet() }
    private val filteredClassRefs by lazy { getClasses(filteredClasses) }
    private val subclassStore: MutableMap<Class<*>, Set<Class<*>>> = ConcurrentHashMap()
    private val superclasses: MutableMap<String, Set<Class<*>>> = ConcurrentHashMap()
    private val classAnnotations: MutableMap<String, Set<Class<*>>> = ConcurrentHashMap()
    private val methodAnnotations: MutableMap<String, Set<Method>> = ConcurrentHashMap()

    fun getClassesAnnotatedWith(annotation: Class<out Annotation>): Set<Class<*>> = annotatedClasses(annotation)
    /**
     * Get all classes wearing an annotation
     */
    fun annotatedClasses(annotation: Class<out Annotation>): Set<Class<*>> = classAnnotations.computeIfAbsent(annotation.canonicalName) {
        filteredClassRefs.filter { it.annotations.any { it.annotationClass.qualifiedName == annotation.canonicalName } }
                .toSet()
    }

    fun getSubClasses(clazz: Class<*>): Set<Class<*>> = subclassStore.computeIfAbsent(clazz) {
        filteredClassRefs.filter { isSubClassesOf(clazz, listOf(it)) }.toSet()
    }

    private tailrec fun isSubClassesOf(clazz: Class<*>, lookingAt: List<Class<*>>): Boolean {
        if (lookingAt.isEmpty()) {
            return false
        }
        val withSupers = lookingAt.map { superClasses(it) }
        if (withSupers.any { it.contains(clazz) }) {
            return true
        }
        return isSubClassesOf(clazz, withSupers.flatten())
    }

    fun superClasses(clazz: Class<*>): Set<Class<*>> =
            superclasses.computeIfAbsent(clazz.canonicalName) {
                (clazz.interfaces + clazz.superclass).filterNotNull().toSet()
            }

    fun getMethodsAnnotatedWith(annotation: Class<out Annotation>): Set<Method> = methodAnnotations.computeIfAbsent(annotation.canonicalName) {
        filteredClassRefs
                .flatMap { it.methods.toList() }
                .filter { it.annotations.any { it::class.java == annotation } }
                .toSet()
    }

    private fun getMethodsMatchParams(vararg paramClasses: Class<*>): Set<Method> = TODO()
    private fun getMethodsReturn(clazz: Class<*>): Set<Method> = TODO()
    private fun getMethodsWithAnyParamAnnotated(annotation: Class<out Annotation>): Set<Method> = TODO()
    private fun getConstructorsAnnotatedWith(annotation: Class<out Annotation>): Set<Constructor<*>> = TODO()
    private fun getConstructorsMatchParams(vararg paramClasses: Class<*>): Set<Constructor<*>> = TODO()
    private fun getConstructorsWithAnyParamAnnotated(annotation: Class<out Annotation>): Set<Constructor<*>> = TODO()
    private fun getFieldsAnnotatedWith(annotation: Class<out Annotation>): Set<Field> = TODO()
    private fun getResources(predicate: Predicate<String>): Set<String> = TODO()
    private fun getMethodParamNames(method: Method): List<String> = TODO()
    private fun getConstructorParamNames(constructor: Constructor<*>): List<String> = TODO()
    private fun getFieldUsage(field: Field): Set<Member> = TODO()
    private fun getMethodUsage(method: Method): Set<Member> = TODO()
    private fun getConstructorUsage(constructor: Constructor<*>): Set<Member> = TODO()
}
