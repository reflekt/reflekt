@file:Suppress("UNUSED_PARAMETER", "UNREACHABLE_CODE", "UNCHECKED_CAST", "UNUSED_PARAMETER", "UNUSED_VARIABLE")

package se.jensim.reflekt.internal

import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.junit.Assert.assertThat
import se.jensim.reflekt.RefleKt
import se.jensim.reflekt.internal.TestLeafClass.InternalFieldAnnotation
import se.jensim.reflekt.internal.TestLeafClass.InternalMethodAnnotation
import se.jensim.reflekt.internal.TestLeafClass.InternalParamAnnotation
import se.jensim.reflekt.internal.TestLeafClass.InternalParameter
import se.jensim.reflekt.internal.TestLeafClass.InternalReturn
import java.lang.annotation.Inherited
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.function.Predicate
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER
import kotlin.reflect.jvm.javaConstructor
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaMethod
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RefleKtImplTest {

    private val target: RefleKt = RefleKtImpl()

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

    @Test
    fun `test getMethodsAnnotatedWith`() {
        val result: Set<Method> = target.getMethodsAnnotatedWith(InternalMethodAnnotation::class.java)

        assertEquals(result, setOf(TestLeafClass::asdf.javaMethod))
    }

    @Test
    fun `test getMethodsMatchParams`() {
        val result: Set<Method> = target.getMethodsMatchParams(InternalParameter::class.java)
        assertEquals(result, setOf(TestLeafClass::asdf.javaMethod))
    }

    @Test
    fun `test getMethodsReturn`() {
        val result: Set<Method> = target.getMethodsReturn(InternalReturn::class.java)
        assertEquals(result, setOf(TestLeafClass::asdf.javaMethod))
    }

    @Test
    fun `test getMethodsWithAnyParamAnnotated`() {
        val result: Set<Method> = target.getMethodsWithAnyParamAnnotated(InternalParamAnnotation::class.java)
        assertEquals(result, setOf(TestLeafClass::asdf.javaMethod))
    }

    @Test
    fun `test getConstructorsAnnotatedWith`() {
        val result: Set<Constructor<TestLeafClass>> = target.getConstructorsAnnotatedWith(InternalMethodAnnotation::class.java) as Set<Constructor<TestLeafClass>>
        assertEquals(result, setOf(TestLeafClass::class.constructors.first().javaConstructor))
    }

    @Test
    fun `test getConstructorsMatchParams`() {
        val result: Set<Constructor<TestLeafClass>> = target.getConstructorsMatchParams(InternalParameter::class.java) as Set<Constructor<TestLeafClass>>
        assertEquals(result, setOf(TestLeafClass::class.constructors.first().javaConstructor))
    }

    @Test
    fun `test getConstructorsWithAnyParamAnnotated`() {
        val result: Set<Constructor<TestLeafClass>> = target.getConstructorsWithAnyParamAnnotated(InternalParamAnnotation::class.java) as Set<Constructor<TestLeafClass>>
        assertEquals(result, setOf(TestLeafClass::class.constructors.first().javaConstructor))
    }

    @Test
    fun `test getFieldsAnnotatedWith`() {
        val result: Set<Field> = target.getFieldsAnnotatedWith(InternalFieldAnnotation::class.java)
        assertEquals(result, setOf(TestLeafClass::aFieldToFind.javaField))
    }

    @Test
    fun `test getResources`() {
        val predicate: Predicate<String> = TODO()
        val result: Set<String> = target.getResources(predicate)
    }

    @Test
    fun `test getMethodParamNames`() {
        val result: List<String> = target.getMethodParamNames(TestLeafClass::asdf.javaMethod!!)
        assertEquals(result, listOf("hello"))
    }

    @Test
    fun `test getConstructorParamNames`() {
        val result: List<String> = target.getConstructorParamNames(TestLeafClass::class.constructors.first().javaConstructor!!)
        assertEquals(result, listOf("param"))
    }

    @Test
    fun `test getAllTypes`() {
        val result: Set<String> = target.getAllTypes()

        assertThat(result, not(emptySet()))
    }
}

@Target(CLASS)
internal annotation class OneAnnotation

@OneAnnotation
internal class TestLeafClass() : TestSuperClass() {

    @InternalFieldAnnotation
    val aFieldToFind: InternalParameter = TODO()

    @InternalMethodAnnotation
    constructor(@InternalParamAnnotation param: InternalParameter) : this()

    @InternalMethodAnnotation
    fun asdf(@InternalParamAnnotation hello: InternalParameter): InternalReturn {
        aFieldToFind.toString()
        TODO()
    }

    class InternalParameter
    class InternalReturn
    @Target(VALUE_PARAMETER)
    annotation class InternalParamAnnotation

    @Target(FUNCTION, CONSTRUCTOR)
    annotation class InternalMethodAnnotation

    @Target(FIELD)
    annotation class InternalFieldAnnotation
}

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

@Target(CLASS, FUNCTION)
internal annotation class ThreeAnnotation

