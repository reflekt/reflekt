package se.jensim.reflekt

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import java.lang.annotation.Inherited
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.test.Test

internal class RefleKtTest {

    private val target = RefleKt ()

    @Test
    fun `classes annotated with`() {
        val annotatedClasses = target.getClassesAnnotatedWith(ThreeAnnotation::class.java)

        val expected: Set<Class<*>> = setOf(TestSuperInterface::class)
                .map { it.java }.toSet()
        assertThat(annotatedClasses, equalTo(expected))
    }

    @Test
    fun `inherited annotation`() {
        val annotatedClasses = target.getClassesAnnotatedWith(TwoAnnotationWithInheritance::class.java)

        val expected: Set<Class<*>> = setOf(
                TestSuperDuperClass::class,
                TestSuperClass::class,
                TestLeafClass::class)
                .map { it.java }.toSet()
        assertThat(annotatedClasses, equalTo(expected))
    }

    @Test
    fun `transitive subclasses`() {
        val superClasses = target.getSubClasses(TestSuperInterface::class.java)

        val expected: Set<Class<*>> = setOf(
                TestInterface::class,
                TestSuperDuperClass::class,
                TestSuperClass::class,
                TestLeafClass::class)
                .map { it.java }.toSet()
        assertThat(superClasses, equalTo(expected))
    }
}

@Target(CLASS)
internal annotation class OneAnnotation

@OneAnnotation
internal class TestLeafClass : TestSuperClass()

@OneAnnotation
internal abstract class TestSuperClass : TestSuperDuperClass()

@TwoAnnotationWithInheritance
internal abstract class TestSuperDuperClass : TestInterface

internal interface TestInterface : TestSuperInterface

@ThreeAnnotation
internal interface TestSuperInterface

@Inherited
@Target(CLASS)
internal annotation class TwoAnnotationWithInheritance

@Target(CLASS)
internal annotation class ThreeAnnotation

