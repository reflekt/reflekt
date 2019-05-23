package se.jensim.reflekt

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.Matchers.equalTo
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
        val expected = setOf(
                OneAnnotation::class,
                TwoAnnotation::class,
                ThreeAnnotation::class,
                Metadata::class)
                .map { it.java.canonicalName }.toSet()

        assertThat(annotations, equalTo(expected))
    }

    @org.junit.Test
    fun `classes annotated with`() {

        val annotatedClasses = target.getClassesAnnotatedWith(ThreeAnnotation::class.java.canonicalName)

        val expected = setOf(
                TestInterface::class,
                TestSuperDuperClass::class,
                TestSuperClass::class,
                TestLeafClass::class)
                .map { it.java.canonicalName }.toSet()
        assertThat(annotatedClasses, equalTo(expected))
    }

    @Test
    fun `transitive super classes`() {
        val superClasses = target.getTransitiveSuperClasses(TestLeafClass::class.java.canonicalName)

        val expected = setOf(
                TestSuperInterface::class,
                TestInterface::class,
                TestSuperDuperClass::class,
                TestSuperClass::class,
                java.lang.Object::class)
                .map { it.java.canonicalName }.toSet()
        assertThat(superClasses, equalTo(expected))
    }

    @Test
    fun `transitive subclasses`() {
        val superClasses = target.getSubClasses(TestSuperInterface::class.java.canonicalName)

        val expected = setOf(
                TestInterface::class,
                TestSuperDuperClass::class,
                TestSuperClass::class,
                TestLeafClass::class)
                .map { it.java.canonicalName }.toSet()
        assertThat(superClasses, equalTo(expected))
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

