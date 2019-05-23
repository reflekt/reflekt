package se.jensim.reflekt

class RefleKt(conf: RefleKtConf) {

    private val classFileLocator: List<ClassFileLocator> = conf.classFileLocators.toList()
    private val packageFilter: (String) -> Boolean = { className ->
        conf.packageFilter?.let { className.startsWith(it) } ?: true
    }

    private val classes = mutableMapOf<String, Class<*>>()
    private val superclasses = mutableMapOf<String, Set<String>>()
    private val annotations = mutableMapOf<String, Collection<String>>()
    private val transitiveSuperClasses = mutableMapOf<String, Set<String>>()
    private val transitiveAnnotations = mutableMapOf<String, Set<String>>()
    private val subclasses: Map<String, Set<String>>
    private val transitiveSubClasses: Map<String, Set<String>>

    constructor(confDsl: RefleKtConf.() -> Unit) : this(RefleKtConf().apply(confDsl))

    init {
        val classes = getAllClassNames()
                .filter(packageFilter)
                .map { getClass(it) }
        travelUpwards(classes)
        subclasses = generateSubClassMap()
        transitiveSubClasses = generateTransitiveSubClassMap()
    }

    private fun getAllClassNames(): Collection<String> = classFileLocator.flatMap { it.getClasses() }

    private tailrec fun travelUpwards(classes: Collection<Class<*>>) {
        classes.forEach { clazz ->
            annotations.computeIfAbsent(clazz.canonicalName) { clazz.annotations.map { it.annotationClass.qualifiedName!! } }
            superclasses.computeIfAbsent(clazz.canonicalName) { (clazz.interfaces + clazz.superclass).mapNotNull { it?.canonicalName }.toSet() }
        }
        val next = classes.flatMap { (it.interfaces + it.superclass).filterNotNull().toList() }.toSet()
        if (next.isNotEmpty()) {
            travelUpwards(next)
        }
    }

    private fun generateSubClassMap(): Map<String, Set<String>> = superclasses
            .flatMap { (k, v) -> v.map { it to k } }
            .groupBy { it.first }
            .mapValues { it.value.map { it.second }.toSet() }

    private fun generateTransitiveSubClassMap(): Map<String, Set<String>> {
        fun getRecursiveSubClasses(canonicalNames: Set<String>): Set<String> {
            if (canonicalNames.isEmpty()) {
                return emptySet()
            }
            val directsubclasses = canonicalNames.flatMap { subclasses[it].orEmpty() }.toSet()
            return directsubclasses + getRecursiveSubClasses(directsubclasses)
        }
        return (superclasses.keys + superclasses.values.flatten()).map {
            it to getRecursiveSubClasses(setOf(it))
        }.toMap()
    }

    private fun getClass(canonicalName: String) = classes.computeIfAbsent(canonicalName) {
        javaClass.classLoader.loadClass(canonicalName)
    }

    fun getTransitiveSuperClasses(canonicalName: String): Set<String> =
            if (transitiveSuperClasses.containsKey(canonicalName)) {
                transitiveSuperClasses[canonicalName].orEmpty()
            } else {
                val deep = superclasses[canonicalName].orEmpty() + (superclasses[canonicalName]?.flatMap { getTransitiveSuperClasses(it) }?.toSet()
                        ?: emptySet())
                transitiveSuperClasses[canonicalName] = deep
                deep
            }

    fun getClassesAnnotatedWith(annotation: String): Set<String> =
            if (transitiveAnnotations.containsKey(annotation)) {
                transitiveAnnotations[annotation]!!
            } else {
                val directlyAnnotated = annotations.filter { it.value.contains(annotation) }
                        .map { it.key }.toSet()
                 directlyAnnotated.flatMap{getSubClasses(it)}.toSet().also {
                    transitiveAnnotations[annotation] = it
                }
            }

    fun getSubClasses(canonicalName: String) = transitiveSubClasses[canonicalName].orEmpty()
}
