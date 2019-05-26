package com.example.annotations

import se.jensim.reflekt.RefleKt
import java.lang.RuntimeException

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Hello World!")
            val r = RefleKt (            )

            val a = r.getSubClasses(SuperClass::class.java.canonicalName)

            if(a.equals(setOf(LeafClass::class.java.canonicalName))){
                println("Correct!")
            }else{
                System.err.println("Bad result. $a")
                java.lang.System.exit(1)
            }
        }
    }
}

class LeafClass:SuperClass()
abstract class SuperClass
