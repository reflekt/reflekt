package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import se.jensim.reflekt.fileToClassRef
import se.jensim.reflekt.filterClassLikeNames
import java.security.CodeSource
import java.util.zip.ZipInputStream

internal class JarFileClassLocator : ClassFileLocator {

    override fun getClasses(): Set<String> =
            JarFileClassLocator::class.java.protectionDomain.codeSource?.let {
                getClasses(it)
            }.orEmpty()
                    .filterClassLikeNames()
                    .map { it.fileToClassRef() }
                    .toSet()

    private fun getClasses(codeScource: CodeSource): Set<String> {
        ZipInputStream(codeScource.location.openStream()).use {
            return generateSequence { it.nextEntry }.map { it.name }.toSet()
        }
    }
}
