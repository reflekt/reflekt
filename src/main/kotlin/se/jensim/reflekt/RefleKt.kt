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
        val transitiveSubClasses = mutableMapOf<String, Set<String>>()
        classes.keys.forEach { c ->
            transitiveSubClasses.computeIfAbsent(c) {
                generateSequence(subclasses[it]) {
                    val a = it.flatMap { transitiveSubClasses[it].orEmpty() }
                    if (a.isEmpty()) null else a.toSet()
                }.flatten().toSet()
            }
        }
        return transitiveSubClasses
    }

    private fun getClass(canonicalName: String) = classes.computeIfAbsent(canonicalName) {
        javaClass.classLoader.loadClass(canonicalName)
    }

    fun getTransitiveSuperClasses(canonicalName: String): Set<String> = transitiveSuperClasses.computeIfAbsent(canonicalName) {
        generateSequence<Collection<String>>(superclasses[canonicalName].orEmpty()) {
            val a = it.flatMap { getTransitiveSuperClasses(it) }.toSet()
            if (a.isEmpty()) null else a
        }.flatten().toSet()
    }

    fun getTransitiveAnnotations(canonicalName: String): Set<String> = transitiveAnnotations.computeIfAbsent(canonicalName) {
        getTransitiveSuperClasses(it)
                .flatMap { annotations[it].orEmpty() }
                .toSet() + annotations[canonicalName].orEmpty()
    }

    fun getClassesAnnotatedWith(annotation:String):Set<String>{
        TODO()
    }
}
