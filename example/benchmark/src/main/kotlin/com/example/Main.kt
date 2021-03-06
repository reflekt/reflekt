package com.example

import io.github.reflekt.Reflekt
import io.github.reflekt.ReflektBuilder.reflekt
import org.reflections.Reflections

private const val ORG_REFLECTIONS = "org.reflections"
private const val REFLEKT = "RefleKt"

class Main {

    companion object {

        inline fun timed(named: String? = null, action: () -> BenchmarkReflect): Pair<BenchmarkReflect, Long> {
            val start = System.currentTimeMillis()
            val ref = action()
            val stop = System.currentTimeMillis()
            return ref to stop - start
        }

        private var pkgScope: String = "null"

        private val jobs = mapOf(
                ORG_REFLECTIONS to {
                    timed(ORG_REFLECTIONS) {
                        OrgReflectionsBenchmark(pkgScope)
                    }
                },
                REFLEKT to {
                    timed(REFLEKT) {
                        RefleKtBenchmark(pkgScope)
                    }
                }
        )

        @JvmStatic
        fun main(args: Array<String>) {
            val iterations = args[0].toInt()
            pkgScope = args[1]
            val jobs = args.drop(2).map { it to jobs[it] }
            println("Running $iterations iterations on same jvm with package limit to \"$pkgScope\" ${jobs.map { it.first }}!")
            jobs.map { (name, jobConstructor) ->
                val (job, initTime) = jobConstructor!!.invoke()
                val results = (1..iterations).map {
                    benchMark(job) // + if(it == 1) initTime else 0
                }
                BenchmarkResult(name, initTime, results)
            }.prettyPrint()
        }

        fun benchMark(impl: BenchmarkReflect): Long? {
            val start = System.currentTimeMillis()
            val a = impl.getSubClassesOf(SuperClass::class.java)
            val b = impl.getSubClassesOf(SuperClass2::class.java)
            val c = impl.getSubClassesOf(Object::class.java)

            val timeTaken = System.currentTimeMillis() - start

            val ok = if (a == setOf(LeafClass::class.java)) {
                b == setOf(LeafClass::class.java, SuperClass::class.java)
            } else {
                false
            }
            if (!ok) {
                if (impl.doFailHard()) {
                    System.err.println("${impl.name} failed! a=$a b=$b")
                    System.exit(1)
                }
                return null
            } else {
                return timeTaken
            }
        }
    }
}

fun List<BenchmarkResult>.prettyPrint() {
    table().with(
            "Name" to BenchmarkResult::name,
            "Failed" to { it -> "${it.failed}" },
            "Init" to { it -> "${it.initTime}ms" },
            "Init+First" to { it -> "${it.initAndFirst}ms" },
            "Max" to { it -> "${it.max}ms" },
            "Min" to { it -> "${it.min}ms" },
            "Avg" to { it -> "${it.avg}ms" }
    ).println()
    println("=================================================")
}

data class BenchmarkResult(val name: String, val initTime: Long, val runs: List<Long?>) {
    val initAndFirst = runs[0]?.let { "${it + initTime} " } ?: "-"
    val failed = runs.size - runs.filterNotNull().size
    val max = runs.filterNotNull().max()?.toString() ?: "-"
    val min = runs.filterNotNull().min()?.toString() ?: "-"
    val avg = runs.filterNotNull().average().roundTo(2)
    fun Double.roundTo(decimals: Int): Double = times(10 * decimals).toLong().div(10.0 * decimals)
}

class LeafClass : SuperClass()
abstract class SuperClass : SuperClass2()

abstract class SuperClass2
abstract class BenchmarkReflect(pkgScope: String) {
    val pkg = if (pkgScope == "null") null else pkgScope
    abstract val name: String
    abstract fun getSubClassesOf(clazz: Class<*>): Set<Class<*>>
    abstract fun getAnnotatedWith(clazz: Class<out Annotation>): Set<Class<*>>
    abstract fun doFailHard(): Boolean
}

class RefleKtBenchmark(pkgScope: String) : BenchmarkReflect(pkgScope) {
    override val name = REFLEKT
    private val refleKt: Reflekt = this.pkg?.let { reflekt(it) } ?: reflekt()
    override fun getSubClassesOf(clazz: Class<*>): Set<Class<*>> = refleKt.getSubClasses(clazz)
    override fun getAnnotatedWith(clazz: Class<out Annotation>): Set<Class<*>> = refleKt.getClassesAnnotatedWith(clazz)
    override fun doFailHard(): Boolean = true
}

class OrgReflectionsBenchmark(pkgScope: String) : BenchmarkReflect(pkgScope) {
    override val name: String = ORG_REFLECTIONS
    private val reflections: Reflections = this.pkg?.let { Reflections(it) } ?: Reflections()
    override fun getSubClassesOf(clazz: Class<*>): Set<Class<*>> = reflections.getSubTypesOf(clazz)
    override fun getAnnotatedWith(clazz: Class<out Annotation>): Set<Class<*>> = reflections.getTypesAnnotatedWith(clazz)
    override fun doFailHard(): Boolean = false
}
