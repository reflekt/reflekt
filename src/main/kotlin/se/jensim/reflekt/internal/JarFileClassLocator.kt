package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import se.jensim.reflekt.classRegexp
import se.jensim.reflekt.fileToClassRef
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

internal class JarFileClassLocator : ClassFileLocator {

    companion object {

        private val classFiles: Set<String> by lazy {
            JarFileClassLocator::class.java.protectionDomain.codeSource
                    ?.let { ZipFile(File(it.location.toURI())) }
                    ?.let { getClassFiles(it) }
                    .orEmpty()
        }

        fun getClassFiles(zipFile: ZipFile): Set<String> =
                getClassFiles(zipFile, emptySet(), zipFile.entries()?.toList().orEmpty())
                        .map { it.fileToClassRef() }.toSet()

        private tailrec fun getClassFiles(zipFile: ZipFile, foundClassFiles: Set<String>, jarFileEntries: Collection<ZipEntry>): Set<String> {
            if (jarFileEntries.isEmpty()) {
                return foundClassFiles
            }

            val newClasses = jarFileEntries.map { it.name }.filter { it.matches(classRegexp) }
            val jars = jarFileEntries.filter { !it.isDirectory && it.name.endsWith(".jar") }
                    .mapNotNull { zipFile.getInputStream(it) }.map { ZipInputStream(it) }
            val nextLevel = jars.flatMap { generateSequence { it.nextEntry }.toList() }

            return getClassFiles(zipFile, foundClassFiles + newClasses, nextLevel)
        }
    }

    override fun getClasses(): Set<String> = classFiles
}
