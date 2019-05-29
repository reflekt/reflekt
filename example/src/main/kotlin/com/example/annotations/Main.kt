package com.example.annotations

import org.reflections.Reflections
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
            println("Running $iterations iterations on same jvm with package limit to \"$pkgScope\" ${this.jobs.keys}!")
            jobs.forEach { (name, jobConstructor) ->
                val (job, initTime) = jobConstructor!!.invoke()
                val results = (1..iterations).map {
                    benchMark(job)
                }
                val jobName = name.padEnd(jobNamePadLength, ' ')
                println("[$jobName] Init=${initTime}ms Max=${results.max()}ms Min=${results.min()}ms, Avg=${results.average().roundTo(2)}ms")
            }
        }

        fun benchMark(impl: BenchmarkReflect): Long {
            val start = System.currentTimeMillis()
            val a = impl.getSubClassesOf(SuperClass::class.java)
            val b = impl.getSubClassesOf(SuperClass2::class.java)

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
    private val refleKt = this.pkg?.let { RefleKt { packageFilter = it } } ?: RefleKt()
    override fun getSubClassesOf(clazz: Class<*>): Set<Class<*>> = refleKt.getSubClasses(clazz)
    override fun getAnnotatedWith(clazz: Class<out Annotation>): Set<Class<*>> = refleKt.getClassesAnnotatedWith(clazz)
}

class OrgReflectionsBenchmark(pkgScope: String) : BenchmarkReflect(pkgScope) {
    override val name: String = "org.reflections"
    private val reflections = this.pkg?.let { Reflections(it) } ?: Reflections()
    override fun getSubClassesOf(clazz: Class<*>): Set<Class<*>> = reflections.getSubTypesOf(clazz)
    override fun getAnnotatedWith(clazz: Class<out Annotation>): Set<Class<*>> = reflections.getTypesAnnotatedWith(clazz)
}
