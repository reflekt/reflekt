package com.example.annotations

import org.reflections.Reflections
import se.jensim.refleKt
import se.jensim.reflekt.RefleKt

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
                "org.reflections" to {
                    timed("org.reflections") {
                        OrgReflectionsBenchmark(pkgScope)
                    }
                },
                "reflekt" to {
                    timed("RefleKt") {
                        RefleKtBenchmark(pkgScope)
                    }
                }
        )

        @JvmStatic
        fun main(args: Array<String>) {
            val iterations = args[0].toInt()
            pkgScope = args[1]
            val jobNamePadLength = jobs.keys.maxBy { it.length }?.length ?: 10
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

        fun benchMark(impl: BenchmarkReflect): Long {
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
                System.err.println("${impl.name} failed! a=$a b=$b")
                System.exit(1)
            }
            return timeTaken
        }

        private fun Double.roundTo(decimals: Int = 2): Double =
                times(10 * decimals).toLong().div(10.0 * decimals)
    }
}

fun List<BenchmarkResult>.prettyPrint() {
    val nameMax = map { it.name.length }.max()!!
    val initTimeMax = map { it.initTime.toString().length }.max()!!
    val initAndFirstMax = map { it.initAndFirst.toString().length }.max()!!
    val maxMax = map { it.max.toString().length }.max()!!
    val minMax = map { it.min.toString().length }.max()!!
    val avgMax = map { it.avg.toString().length }.max()!!
    forEach {
        val jobName = it.name.padEnd(nameMax, '_')
        val init = "Init=${it.initTime.toString().padStart(initTimeMax, '_')}ms"
        val initFirst = "Init+First=${it.initAndFirst.toString().padStart(initAndFirstMax, '_')}ms"
        val max = "Max=${it.max.toString().padStart(maxMax, '_')}ms"
        val min = "Min=${it.min.toString().padStart(minMax, '_')}ms"
        val avg = "Avg=${it.avg.toString().padStart(avgMax, '_')}ms"
        println("[$jobName] $init, $initFirst, $max, $min, $avg")
    }
    println("===============================================")
}

data class BenchmarkResult(val name: String, val initTime: Long, val runs: List<Long>) {
    val initAndFirst = initTime + runs[0]
    val max = runs.max()!!
    val min = runs.min()!!
    val avg = runs.average().toLong()
}

class LeafClass : SuperClass()
abstract class SuperClass : SuperClass2()
abstract class SuperClass2

abstract class BenchmarkReflect(pkgScope: String) {
    val pkg = if (pkgScope == "null") null else pkgScope
    abstract val name: String
    abstract fun getSubClassesOf(clazz: Class<*>): Set<Class<*>>
    abstract fun getAnnotatedWith(clazz: Class<out Annotation>): Set<Class<*>>
}

class RefleKtBenchmark(pkgScope: String) : BenchmarkReflect(pkgScope) {
    override val name = "RefleKt"
    private val refleKt: RefleKt = this.pkg?.let { refleKt(it) } ?: refleKt()
    override fun getSubClassesOf(clazz: Class<*>): Set<Class<*>> = refleKt.getSubClasses(clazz)
    override fun getAnnotatedWith(clazz: Class<out Annotation>): Set<Class<*>> = refleKt.getClassesAnnotatedWith(clazz)
}

class OrgReflectionsBenchmark(pkgScope: String) : BenchmarkReflect(pkgScope) {
    override val name: String = "org.reflections"
    private val reflections: Reflections = this.pkg?.let { Reflections(it) } ?: Reflections()
    override fun getSubClassesOf(clazz: Class<*>): Set<Class<*>> = reflections.getSubTypesOf(clazz)
    override fun getAnnotatedWith(clazz: Class<out Annotation>): Set<Class<*>> = reflections.getTypesAnnotatedWith(clazz)
}
