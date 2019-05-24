package se.jensim.reflekt

class RefleKtConf {

    var packageFilter: String = ""
    var classFileLocatorConf: ClassFileLocatorConf = ClassFileLocatorConf()

    fun classFileLocatorConf(conf: ClassFileLocatorConf.() -> Unit) {
        classFileLocatorConf.also(conf)
    }

    class ClassFileLocatorConf {
        var disableAllDefaultClassFileLocators = false
        var classPathClassFileLocator = true
        var deepJarClassFileLocator = false
        val extraClassFileLocator = mutableListOf<ClassFileLocator>()
    }
}
