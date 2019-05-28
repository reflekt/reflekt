package se.jensim.reflekt.internal

import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

object ReflektStore {

    private val classes: MutableMap<String, Class<*>?> = ConcurrentHashMap()
    private val superclasses: MutableMap<String, Set<Class<*>>> = ConcurrentHashMap()
    private val annotations: MutableMap<String, Set<Class<*>>> = ConcurrentHashMap()

    fun getClass(clazz: String): Class<*>? = classes.computeIfAbsent(clazz) {
        try {
            Class.forName(it)
        } catch (e: Exception) {
            null
        } catch (e: NoClassDefFoundError) {
            null
        }
    }

    @SuppressWarnings("unchecked")
    fun getClasses(classes: Collection<String>): Set<Class<*>> = if (classes.size >= 25)
        classes.parallelStream().map { getClass(it) }
                .filter { it != null }
                .collect(Collectors.toSet()) as Set<Class<*>>
    else classes.mapNotNull { getClass(it) }.toSet()

    fun superClasses(clazz: Class<*>): Set<Class<*>> =
            superclasses.computeIfAbsent(clazz.canonicalName) {
                (clazz.interfaces + clazz.superclass).filterNotNull().toSet()
            }

    fun annotatedClasses(annotation: Class<*>): Set<Class<*>> = annotations.computeIfAbsent(annotation.canonicalName) {
        classes.values.filterNotNull()
                .filter { it.annotations.any { it.annotationClass.qualifiedName == annotation.canonicalName } }
                .toSet()
    }

    /*
    fun noticeClasses(batch:Collection<Class<*>>){
        batch.forEach { classes.putIfAbsent(it.canonicalName, it) }
        //TODO("Add come async mechanism, coroutine?")
    }
    */
}
