package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import java.io.File

internal class ClassFileLocatorImpl : ClassFileLocator {

    private val classLoader = Thread.currentThread().contextClassLoader

    override fun getClasses(): Set<String> {
        val files = File(classLoader.getResource("./").toURI())
        val allClassFiles = visit(listOf(files))
        return allClassFiles.filterAndRenameAsClassRefs()
    }

    private tailrec fun visit(files: List<File>, classFiles: List<File> = emptyList()): List<File> {
        val subs: List<File> = files.filter { it.isDirectory }.flatMap { it.listFiles().toList() }
        val foundClassFiles = files.filter { it.isClassFile() }
        if (subs.isEmpty()) {
            return classFiles + foundClassFiles
        }
        return visit(subs, classFiles + foundClassFiles)
    }

    private fun File.isClassFile() = isFile
            && toURI().toString().matches(Regex("^.*/[A-Z]+[A-Za-z0-9]*\\.class$"))

    private fun List<File>.filterAndRenameAsClassRefs(): Set<String> {
        val dirName = classLoader.getResource("./").toURI().toString()
        val dirNameLength = dirName.length
        return map {
            it.toURI().toString().drop(dirNameLength).dropLast(6).replace("/", ".")
        }.toSet()
    }
}
