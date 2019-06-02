package se.jensim.reflekt

class RefleKtConf {

    /**
     * Filter the classes available for reflection, used as a class-canonicalName prefix.
     * A class named com.example.Myclass can then be matched with "com.exa", but not with "om.example".
     */
    var packageFilter: String = ""

    /**
     * Configuration container for the class file locators
     * @see ClassFileLocator
     */
    var classFileLocatorConf: ClassFileLocatorConf = ClassFileLocatorConf()

    /**
     * Configuration container for the class file locators
     * @see ClassFileLocator
     */
    fun classFileLocatorConf(conf: ClassFileLocatorConf.() -> Unit) {
        classFileLocatorConf.also(conf)
    }

    /**
     * Configuration container for the class file locators
     * @see ClassFileLocator
     */
    class ClassFileLocatorConf {

        /**
         * By default RefleKt will look recursively in classpathDirectories and inside the running jar, if applicable.
         * These are added to the extraClassFileLocator list
         * @see se.jensim.reflekt.internal.JarFileClassLocator
         * @see se.jensim.reflekt.internal.ClassPathClassFileLocator
         */
        var disableAllDefaultClassFileLocators = false

        /**
         * Instruction to the default classFileLocators weather or not to deep scan jars inside the running jar or jars on the classpath recursively.
         */
        var includeNestedJars = false

        /**
         * Made your own ClassFileLocator? Append it here.
         */
        val extraClassFileLocator = mutableListOf<ClassFileLocator>()
    }
}
