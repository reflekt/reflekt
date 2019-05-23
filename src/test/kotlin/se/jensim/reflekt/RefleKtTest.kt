package se.jensim.reflekt

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.Matcher
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat
import kotlin.test.Test

class RefleKtTest {

    val target = RefleKt {
        classFileLocators = mutableListOf(mock {
            on { getClasses() } doReturn setOf(TestLeafClass::class.java.canonicalName)
        })
    }

    @Test
    fun `transitive annotations`() {
        val annotations = target.getTransitiveAnnotations(TestLeafClass::class.java.canonicalName)

        println(annotations)
        assertThat(annotations, hasSize(3))
        val expected = setOf(OneAnnotation::class, TwoAnnotation::class, ThreeAnnotation::class)
                .map { it.java.canonicalName }.toSet()

        //assertThat(annotations, containsInAnyOrder(expected))
    }

    @org.junit.Test
    fun `classes annotated with`() {

        val annotatedClasses = target.getClassesAnnotatedWith(ThreeAnnotation::class.java.canonicalName)

        val expected = setOf(OneAnnotation::class, TwoAnnotation::class, ThreeAnnotation::class)
                .map { it.java.canonicalName }.toSet()

        assertThat(annotatedClasses, hasSize(3))
        assertThat(annotatedClasses, containsInAnyOrder(expected) as Matcher<Collection<String>>)
    }

    @Test
    fun `transitive super classes`() {
        val superClasses = target.getTransitiveSuperClasses(TestLeafClass::class.java.canonicalName)

        println(superClasses)
        val expected = setOf(TestSuperInterface::class, TestInterface::class, TestSuperDuperClass::class, TestSuperClass::class)
                .map { it.java.canonicalName }.toSet()
        assertThat(superClasses, containsInAnyOrder(expected) as Matcher<Collection<String>>)
    }

    @Test
    fun `transitive subclasses`() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

@TwoAnnotation
annotation class OneAnnotation

@OneAnnotation
class TestLeafClass : TestSuperClass()

annotation class TwoAnnotation

@OneAnnotation
open class TestSuperClass : TestSuperDuperClass()

abstract class TestSuperDuperClass : TestInterface

interface TestInterface : TestSuperInterface

@ThreeAnnotation
interface TestSuperInterface

annotation class ThreeAnnotation

