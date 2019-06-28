package io.github.reflekt.internal;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.ReflektAllConstructors;
import io.github.reflekt.ReflektConstructorsAnnotatedWith;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class ReflektConstructorsAnnotatedWithImplTest {

    private ReflektAllConstructors mocka = mock(ReflektAllConstructors.class);
    private final ReflektConstructorsAnnotatedWith target = new ReflektConstructorsAnnotatedWithImpl(LazyBuilder.lazy(() -> mocka));

    @Test
    public void testGetConstructorsAnnotatedWith() {
        // given
        Set<Constructor> testConstructors = Arrays.stream(MyTestClass.class.getConstructors()).collect(toSet());
        when(mocka.getAllConstructors()).thenReturn(testConstructors);

        // when
        Set<String> result = target.getConstructorsAnnotatedWith(AnAnnotation.class).stream()
                .map(Constructor::getParameterTypes)
                .filter(a -> a.length >= 2)
                .map(a -> a[1].getSimpleName())
                .collect(toSet());

        // then
        assertThat(result, is(new HashSet<>(Arrays.asList("Integer", "Boolean"))));
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
