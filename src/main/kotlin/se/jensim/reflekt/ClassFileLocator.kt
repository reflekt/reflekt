package se.jensim.reflekt

interface ClassFileLocator {

    fun getClasses(): Set<String>
}

internal val classRegexp = Regex("^.*/([A-Z]+[A-Za-z0-9]${"$"})*[A-Z]+[A-Za-z0-9]*\\.class$")
internal fun String.fileToClassRef() = dropLast(6).replace("/", ".").replace("$", ".")
