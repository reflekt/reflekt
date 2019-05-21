package se.jensim.reflekt

import javassist.ClassPool

class RefleKt {

    fun findClass (): Class<*>? {
        val cp = ClassPool.getDefault().apply {
            appendClassPath("/se/jensim")
        }
        return try {
            cp.get("se.jensim.reflekt.RefleKt").toClass()
        }catch (e:Exception){
            RefleKt::class.java
        }

    }
}
