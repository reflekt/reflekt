package se.jensim.reflekt

import se.jensim.reflekt.internal.ReflektStore.annotatedClasses
import se.jensim.reflekt.internal.ReflektStore.getClasses
import se.jensim.reflekt.internal.ReflektStore.superClasses
import se.jensim.reflekt.internal.getClassFileLocators
import se.jensim.reflekt.internal.packageFilter
import java.util.concurrent.ConcurrentHashMap

class RefleKt(conf: RefleKtConf) {

    private val classFileLocators = conf.getClassFileLocators()
    private val packageFilter = conf.packageFilter()

    constructor(confDsl: RefleKtConf.() -> Unit) : this(RefleKtConf().apply(confDsl))
    constructor() : this(RefleKtConf())

    private val allClasses = classFileLocators.flatMap { it.getClasses(conf.classFileLocatorConf.includeNestedJars) }
    private val filteredClasses = allClasses.filter(packageFilter).toSet()
    private val filteredClassRefs = getClasses(filteredClasses)
    private val subclassStore: MutableMap<Class<*>, Set<Class<*>>> = ConcurrentHashMap()

    fun getClassesAnnotatedWith(annotation: Class<out Annotation>): Set<Class<*>> = annotatedClasses(annotation)

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
}
