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

internal class RefleKtImpl(conf: RefleKtConf):RefleKt {

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

    override fun getAllTypes(): Set<String> = filteredClasses

    /**
     * Get all classes wearing an annotation
     */
    override fun getClassesAnnotatedWith(annotation: Class<out Annotation>): Set<Class<*>> = classAnnotations.computeIfAbsent(annotation.canonicalName) {
        filteredClassRefs.filter { it.annotations.any { it.annotationClass.qualifiedName == annotation.canonicalName } }
                .toSet()
    }

    override fun getSubClasses(clazz: Class<*>): Set<Class<*>> = subclassStore.computeIfAbsent(clazz) {
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

    private fun superClasses(clazz: Class<*>): Set<Class<*>> =
            superclasses.computeIfAbsent(clazz.canonicalName) {
                (clazz.interfaces + clazz.superclass).filterNotNull().toSet()
            }

    override fun getMethodsAnnotatedWith(annotation: Class<out Annotation>): Set<Method> = methodAnnotations.computeIfAbsent(annotation.canonicalName) {
        filteredClassRefs
                .flatMap { it.methods.toList() }
                .filter { it.annotations.any { it::class.java == annotation } }
                .toSet()
    }

    override fun getMethodsMatchParams(vararg paramClasses: Class<*>): Set<Method> = TODO()
    override fun getMethodsReturn(clazz: Class<*>): Set<Method> = TODO()
    override fun getMethodsWithAnyParamAnnotated(annotation: Class<out Annotation>): Set<Method> = TODO()
    override fun getConstructorsAnnotatedWith(annotation: Class<out Annotation>): Set<Constructor<*>> = TODO()
    override fun getConstructorsMatchParams(vararg paramClasses: Class<*>): Set<Constructor<*>> = TODO()
    override fun getConstructorsWithAnyParamAnnotated(annotation: Class<out Annotation>): Set<Constructor<*>> = TODO()
    override fun getFieldsAnnotatedWith(annotation: Class<out Annotation>): Set<Field> = TODO()
    override fun getResources(predicate: Predicate<String>): Set<String> = TODO()
    override fun getMethodParamNames(method: Method): List<String> = TODO()
    override fun getConstructorParamNames(constructor: Constructor<*>): List<String> = TODO()
    override fun getFieldUsage(field: Field): Set<Member> = TODO()
    override fun getMethodUsage(method: Method): Set<Member> = TODO()
    override fun getConstructorUsage(constructor: Constructor<*>): Set<Member> = TODO()
}
