package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import se.jensim.reflekt.classRegexp
import se.jensim.reflekt.fileToClassRef
import java.io.File
import java.util.zip.ZipFile

internal object ClassFileLocatorImpl : ClassFileLocator() {

    private val stickyClasses: Set<String> by lazy {
        val rootUris = Thread.currentThread().contextClassLoader
                .getResources("./").toList().map { it.toURI() }
        val files = rootUris.map { File(it) }.map { FileWithRoot(it, it) }
        visit(files)
                .map { it.getClassName() }
                .toSet()
    }

    override fun getClasses(): Set<String> = stickyClasses

    private tailrec fun visit(files: List<FileWithRoot>, classFiles: List<ClassFile> = emptyList()): List<ClassFile> {
        val subs: List<FileWithRoot> = files.subFiles()
        val foundClassFiles = files.filter { it.file.isClassFile() }
        val classesFromJar = files.filter { it.file.name.endsWith("jar") }
                .map { ZipFile(it.file) }
                .flatMap { JarFileClassLocator.getClasses(it).map { ClassWithoutFile(it) } }
        val sum = classFiles + foundClassFiles + classesFromJar
        if (subs.isEmpty()) {
            return sum
        }
        return visit(subs, sum)
    }

    private fun File.isClassFile() = isFile &&
            toURI().toString().matches(classRegexp)

    private fun List<FileWithRoot>.subFiles(): List<FileWithRoot> = filter { it.file.isDirectory }
            .flatMap { a -> a.file.listFiles().map { FileWithRoot(a.root, it) } }

    interface ClassFile {
        fun getClassName(): String
    }

    class ClassWithoutFile(private val clazzName: String) : ClassFile {
        override fun getClassName(): String = clazzName
    }

    class FileWithRoot(val root: File, val file: File) : ClassFile {
        override fun getClassName(): String = file.toURI().toString()
                .drop(root.toURI().toString().length)
                .fileToClassRef()
    }
}
