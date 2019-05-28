package com.example.annotations

import org.reflections.Reflections
import se.jensim.reflekt.RefleKt

class Main {

    companion object {

        inline fun timed(named: String? = null, action: () -> Unit) {
            val start = System.currentTimeMillis()
            action()
            val stop = System.currentTimeMillis()
            val ref = named?.let { " [$it]" } ?: ""
            if (named != null) {
                println("Action$ref took ${stop - start} ms executing")
            }
        }

        private var pkgScope: String? = null

        private val jobs = mapOf(
                "org.reflections" to {
                    timed("org.reflections") {
                        //val r = Reflections("com.example.annotations")
                        val r = pkgScope?.let { Reflections(it) } ?: Reflections()
                        val a = r.getSubTypesOf(SuperClass::class.java)
                        val b = r.getSubTypesOf(SuperClass2::class.java)
                        val ok = if (a == setOf(LeafClass::class.java)) {
                            b == setOf(LeafClass::class.java, SuperClass::class.java)
                        } else {
                            false
                        }
                        if (!ok) {
                            System.err.println("org.reflections failed!")
                        }
                    }
                },
                "reflekt" to {
                    timed("RefleKt") {
                        //val r = RefleKt { packageFilter = "com.example.annotations" }
                        val r = pkgScope?.let { RefleKt { packageFilter = it } } ?: RefleKt()
                        val a = r.getSubClasses(SuperClass::class.java)
                        val b = r.getSubClasses(SuperClass2::class.java)

                        val ok = if (a == setOf(LeafClass::class.java)) {
                            b == setOf(LeafClass::class.java, SuperClass::class.java)
                        } else {
                            false
                        }
                        if (!ok) {
                            System.err.println("reflekt failed! a=$a b=$b")
                        }

                    }
                }
        )

        @JvmStatic
        fun main(args: Array<String>) {
            val iterations = args[0].toInt()
            pkgScope = if (args[1] == "null") null else args[1]
            val jobs = args.drop(2).map { it to jobs[it] }.toMap()
            println("Running $iterations iterations on same jvm with package limit to \"$pkgScope\" ${jobs.keys}!")
            repeat(iterations) {
                jobs.forEach { (name, job) ->
                    job?.invoke() ?: System.err.println("Job $name is not defined")
                }
            }
        }
    }
}

class LeafClass : SuperClass()
abstract class SuperClass : SuperClass2()
abstract class SuperClass2
