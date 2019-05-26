package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import se.jensim.reflekt.classRegexp
import se.jensim.reflekt.fileToClassRef
import java.io.File

object ClassFileLocatorImpl : ClassFileLocator {

    private val stickyClasses: Set<String> by lazy {
        val rootUris = Thread.currentThread().contextClassLoader
                .getResources("./").toList().map { it.toURI() }
        val files = rootUris.map { File(it) }.map { FileWithRoot(it, it) }
        val allClassFiles = visit(files)
        allClassFiles.fileToClassRef()
    }

    override fun getClasses(): Set<String> = stickyClasses

    private tailrec fun visit(files: List<FileWithRoot>, classFiles: List<FileWithRoot> = emptyList()): List<FileWithRoot> {
        val subs: List<FileWithRoot> = files.subFiles()
        val foundClassFiles = files.filter { it.file.isClassFile() }
        if (subs.isEmpty()) {
            return classFiles + foundClassFiles
        }
        return visit(subs, classFiles + foundClassFiles)
    }

    private fun File.isClassFile() = isFile &&
            toURI().toString().matches(classRegexp)

    private fun List<FileWithRoot>.subFiles(): List<FileWithRoot> = filter { it.file.isDirectory }
            .flatMap { a -> a.file.listFiles().map { FileWithRoot(a.root, it) } }

    private fun List<FileWithRoot>.fileToClassRef(): Set<String> = map {
        it.file.toURI().toString()
                .drop(it.root.toURI().toString().length)
                .fileToClassRef()
    }.toSet()

    data class FileWithRoot(val root: File, val file: File)
}
