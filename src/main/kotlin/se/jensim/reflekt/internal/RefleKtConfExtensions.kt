package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import se.jensim.reflekt.RefleKtConf

internal fun RefleKtConf.getClassFileLocators(): List<ClassFileLocator> =
        classFileLocatorConf.extraClassFileLocator.apply {
            if (!classFileLocatorConf.disableAllDefaultClassFileLocators) {
                if (classFileLocatorConf.classPathClassFileLocator) {
                    add(ClassFileLocatorImpl())
                    add(JarFileClassLocator())
                }
                if (classFileLocatorConf.deepJarClassFileLocator) {
                    System.err.println("Not implemented")
                }
            }
        }

internal fun RefleKtConf.packageFilter():(String)->Boolean = { className ->
    packageFilter.let { className.startsWith(it) }
}
