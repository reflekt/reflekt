package se.jensim.reflekt.internal

import org.junit.Assert.assertTrue
import org.junit.Test

internal class ClassPathClassFileLocatorTest {

    @Test
    fun getClasses() {
        // given

        // when
        val classes = ClassPathClassFileLocator.getClasses(false)
        println(classes)

        // then
        assertTrue(classes.contains(javaClass.canonicalName))
        assertTrue(classes.contains(ClassPathClassFileLocator::class.java.canonicalName))
    }
}
