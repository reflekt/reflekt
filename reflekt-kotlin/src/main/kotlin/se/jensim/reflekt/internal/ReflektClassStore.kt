package se.jensim.reflekt.internal

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import java.util.stream.Collectors

/**
 * Responsible for resolving and loading classes
 */
internal object ReflektClassStore {

    private val classFuse = AtomicBoolean(false)
    private val classes: MutableMap<String, Class<*>?> = ConcurrentHashMap()

    private fun getClass(clazz: String): Class<*>? = classes.computeIfAbsent(clazz) {
        try {
            classFuse.compareAndSet(false, true)
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
}
