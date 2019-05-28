package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import se.jensim.reflekt.classRegexp
import se.jensim.reflekt.fileToClassRef
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

internal object JarFileClassLocator : ClassFileLocator() {

    private val classFiles: Set<String> by lazy {
        JarFileClassLocator::class.java.protectionDomain.codeSource?.location?.toURI()
                ?.let { if (it.toString().endsWith(".jar")) ZipFile(File(it)) else {
                    println(it)
                    null
                } }
                ?.let { getClasses(it) }
                .orEmpty()
    }

    internal fun getClasses(jarFile: ZipFile): Set<String> =
            getClassFiles(jarFile, emptySet(), jarFile.entries()?.toList().orEmpty())
                    .map { it.fileToClassRef() }.toSet()

    private tailrec fun getClassFiles(zipFile: ZipFile, foundClassFiles: Set<String>, jarFileEntries: Collection<ZipEntry>): Set<String> {
        if (jarFileEntries.isEmpty()) {
            return foundClassFiles
        }

        val newClasses = jarFileEntries.map { it.name }.filter { it.matches(classRegexp) }
        val jars = jarFileEntries.filter { !it.isDirectory && it.name.endsWith(".jar") }
                .map { ZipInputStream(zipFile.getInputStream(it)) }
                .flatMap { it.getEntries() }

        return getClassFiles(zipFile, foundClassFiles + newClasses, jars)
    }

    private fun ZipInputStream.getEntries(): List<ZipEntry> = try {
        use {
            generateSequence { it.nextEntry }.toList()
        }
    } catch (e: Exception) {
        emptyList()
    }

    override fun getClasses(): Set<String> = classFiles
}
