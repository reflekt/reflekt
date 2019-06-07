package se.jensim.reflekt.internal;

import org.junit.Test;
import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektConstructorsAnnotatedWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReflektConstructorsAnnotatedWithImplTest {

    private ReflektAllConstructors mocka = mock(ReflektAllConstructors.class);
    private final ReflektConstructorsAnnotatedWith target = new ReflektConstructorsAnnotatedWithImpl(mocka);

    @Test
    public void testGetConstructorsAnnotatedWith() {
        // given
        Set<Constructor> testConstructors = Arrays.stream(MyTestClass.class.getConstructors()).collect(toSet());
        when(mocka.getAllConstructors()).thenReturn(testConstructors);

        // when
        var result = target.getConstructorsAnnotatedWith(AnAnnotation.class).stream()
                .map(c -> c.getParameterTypes()[0].getSimpleName())
                .collect(toSet());

        // then
        assertThat(result, is(Set.of("Integer", "Short")));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.CONSTRUCTOR)
    private @interface AnAnnotation {
    }

    @SuppressWarnings("unused")
    private class MyTestClass {

        @AnAnnotation
        MyTestClass(Boolean a) {
        }

        MyTestClass(Short b) {
        }

        @AnAnnotation
        MyTestClass(Integer c) {
        }

        MyTestClass() {
        }
    }
}
