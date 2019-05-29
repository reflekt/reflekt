package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import se.jensim.reflekt.classRegexp
import se.jensim.reflekt.fileToClassRef
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

internal object JarFileClassLocator : ClassFileLocator() {

    override fun getClasses(includeNestedJars: Boolean): Set<String> = classFiles.computeIfAbsent(includeNestedJars, ::getClassesFromSelfJar)

    private val classFiles = ConcurrentHashMap<Boolean, Set<String>>()

    private fun getClassesFromSelfJar(includeNestedJars: Boolean): Set<String> {
        val start = System.currentTimeMillis()
        val classes = JarFileClassLocator::class.java.protectionDomain.codeSource?.location?.toURI()
                ?.let {
                    if (it.toString().endsWith(".jar")) ZipFile(File(it)) else {
                        println(it)
                        null
                    }
                }
                ?.let { getClasses(it, includeNestedJars) }
                .orEmpty()
        //println("Read ${classes.size} jar-file classes in ${System.currentTimeMillis() - start}ms")
        return classes.toSet()
    }

    internal fun getClasses(jarFile: ZipFile, includeNestedJars: Boolean): Collection<String> {
        val jarFileEntries = jarFile.entries()?.toList().orEmpty()
        return if (includeNestedJars) {
            getNestedClassFiles(jarFile, emptySet(), jarFileEntries)
        } else {
            getClassFiles(jarFileEntries)
        }.map { it.fileToClassRef() }
    }

    private tailrec fun getNestedClassFiles(zipFile: ZipFile, foundClassFiles: Collection<String>, jarFileEntries: Collection<ZipEntry>): Collection<String> {
        if (jarFileEntries.isEmpty()) {
            return foundClassFiles
        }
        val newClasses = getClassFiles(jarFileEntries)
        val jars = jarFileEntries.filter { !it.isDirectory && it.name.endsWith(".jar") }
                .map { ZipInputStream(zipFile.getInputStream(it)) }
                .flatMap { it.getEntries() }

        return getNestedClassFiles(zipFile, foundClassFiles + newClasses, jars)
    }

    private fun getClassFiles(jarFileEntries: Collection<ZipEntry>) = jarFileEntries.map { it.name }
            .filter { it.matches(classRegexp) }

    private fun ZipInputStream.getEntries(): List<ZipEntry> = try {
        use {
            generateSequence { it.nextEntry }.toList()
        }
    } catch (e: Exception) {
        emptyList()
    }
}
