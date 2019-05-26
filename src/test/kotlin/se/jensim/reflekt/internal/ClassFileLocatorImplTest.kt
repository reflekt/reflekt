package se.jensim.reflekt.internal

import org.junit.Assert.assertTrue
import org.junit.Test

class ClassFileLocatorImplTest {

    @Test
    fun getClasses() {
        // given

        // when
        val classes = ClassFileLocatorImpl.getClasses()
        println(classes)

        // then
        assertTrue(classes.contains(javaClass.canonicalName))
        assertTrue(classes.contains(ClassFileLocatorImpl::class.java.canonicalName))
    }
}
