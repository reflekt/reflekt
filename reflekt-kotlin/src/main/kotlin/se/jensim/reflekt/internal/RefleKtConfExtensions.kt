package se.jensim.reflekt.internal

import se.jensim.reflekt.ClassFileLocator
import se.jensim.reflekt.RefleKtConf

internal fun RefleKtConf.getClassFileLocators(): List<ClassFileLocator> =
        classFileLocatorConf.extraClassFileLocator.apply {
            if (!classFileLocatorConf.disableAllDefaultClassFileLocators) {
                add(ClassPathClassFileLocator)
                add(JarFileClassLocator)
            }
        }

internal fun RefleKtConf.packageFilter(): (String) -> Boolean = { c ->
    packageFilter.let { c.startsWith(it) }
}
