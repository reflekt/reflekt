package se.jensim.reflekt.internal

import se.jensim.reflekt.RefleKt
import se.jensim.reflekt.RefleKtConf
import se.jensim.reflekt.internal.ReflektClassStore.getClasses
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

internal class RefleKtImpl(conf: RefleKtConf) : RefleKt {

    private val classFileLocators = conf.getClassFileLocators()
    private val packageFilter = conf.packageFilter()

    constructor(confDsl: RefleKtConf.() -> Unit) : this(RefleKtConf().apply(confDsl))
    constructor() : this(RefleKtConf())

    private val allClasses by lazy { classFileLocators.flatMap { it.getClasses(conf.classFileLocatorConf.includeNestedJars) } }
    private val filteredClasses by lazy { allClasses.filter(packageFilter).toSet() }
    private val filteredClassRefs by lazy { getClasses(filteredClasses) }
    private val methods by lazy { filteredClassRefs.flatMap { it.declaredMethods.toList() }.toSet() }
    private val constructors by lazy { filteredClassRefs.flatMap { it.constructors.toList() }.toSet() }
    private val fields by lazy { filteredClassRefs.flatMap { it.declaredFields.toList() }.toSet() }

    /**
     * Parent to its children
     */
    private val subclassStore: MutableMap<Class<*>, Set<Class<*>>> = ConcurrentHashMap()
    /**
     * Child to parents (superclass + interfaces)
     */
    private val superclasses: MutableMap<String, Set<Class<*>>> = ConcurrentHashMap()
    /**
     * Annotation to classes
     */
    private val classAnnotations: MutableMap<String, Set<Class<*>>> = ConcurrentHashMap()
    /**
     * Annotation to methods
     */
    private val methodAnnotations: MutableMap<String, Set<Method>> = ConcurrentHashMap()
    /**
     * Annotation to constructors
     */
    private val constructorAnnotations: MutableMap<String, Set<Constructor<*>>> = ConcurrentHashMap()
    /**
     * Annotation to field
     */
    private val fieldAnnotations: MutableMap<String, Set<Field>> = ConcurrentHashMap()

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
                .filter { it.isAnnotationPresent(annotation) }
                .toSet()
    }

    override fun getMethodsMatchParams(vararg paramClasses: Class<*>): Set<Method> =
            methods.filter { it.parameterTypes?.contentEquals(paramClasses) == true }.toSet()

    override fun getMethodsReturn(clazz: Class<*>): Set<Method> = methods.filter { it.returnType == clazz }.toSet()

    override fun getMethodsWithAnyParamAnnotated(annotation: Class<out Annotation>): Set<Method> = methods.filter {
        it.parameters.any { it.isAnnotationPresent(annotation) }
    }.toSet()

    override fun getConstructorsAnnotatedWith(annotation: Class<out Annotation>): Set<Constructor<*>> = constructorAnnotations.computeIfAbsent(annotation.canonicalName) {
        constructors.filter { it.isAnnotationPresent(annotation) }.toSet()
    }

    override fun getConstructorsMatchParams(vararg paramClasses: Class<*>): Set<Constructor<*>> =
        constructors.filter { it.parameterTypes?.contentEquals(paramClasses) == true }.toSet()

    override fun getConstructorsWithAnyParamAnnotated(annotation: Class<out Annotation>): Set<Constructor<*>> = constructors.filter {
        it.parameters.any { it.isAnnotationPresent(annotation) }
    }.toSet()

    override fun getFieldsAnnotatedWith(annotation: Class<out Annotation>): Set<Field> = fieldAnnotations.computeIfAbsent(annotation.canonicalName) {
        fields.filter { it.isAnnotationPresent(annotation) }.toSet()
    }
}
