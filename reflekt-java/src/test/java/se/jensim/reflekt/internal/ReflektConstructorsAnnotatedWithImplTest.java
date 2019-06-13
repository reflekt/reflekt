package se.jensim.reflekt.internal;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;
import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektConstructorsAnnotatedWith;

public class ReflektConstructorsAnnotatedWithImplTest {

    private ReflektAllConstructors mocka = mock(ReflektAllConstructors.class);
    private final ReflektConstructorsAnnotatedWith target = new ReflektConstructorsAnnotatedWithImpl(lazy(() -> mocka));

    @Test
    public void testGetConstructorsAnnotatedWith() {
        // given
        Set<Constructor> testConstructors = Arrays.stream(MyTestClass.class.getConstructors()).collect(toSet());
        when(mocka.getAllConstructors()).thenReturn(testConstructors);

        // when
        var result = target.getConstructorsAnnotatedWith(AnAnnotation.class).stream()
                .map(Constructor::getParameterTypes)
                .filter(a -> a.length >= 2)
                .map(a -> a[1].getSimpleName())
                .collect(toSet());

        // then
        assertThat(result, is(Set.of("Integer", "Boolean")));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.CONSTRUCTOR)
    private @interface AnAnnotation {
    }

    @SuppressWarnings("unused")
    private class MyTestClass {

        @AnAnnotation
        public MyTestClass(Boolean a) {
        }

        public MyTestClass(Short b) {
        }

        @AnAnnotation
        public MyTestClass(Integer c) {
        }

        public MyTestClass() {
        }
    }
}
