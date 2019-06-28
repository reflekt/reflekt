package io.github.reflekt.internal;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.ReflektAllMethods;
import io.github.reflekt.ReflektMethodsAnnotatedWith;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class ReflektMethodsAnnotatedWithImplTest {

    private ReflektAllMethods mocka = mock(ReflektAllMethods.class);
    private final ReflektMethodsAnnotatedWith target = new ReflektMethodsAnnotatedWithImpl(LazyBuilder.lazy(() -> mocka));

    @Test
    public void testGetMethodsAnnotatedWith() throws NoSuchMethodException {
        // given
        when(mocka.getAllMethods()).thenReturn(new HashSet<>(Arrays.asList(ATestClass.class.getDeclaredMethods())));

        // when
        Set<Method> result = target.getMethodsAnnotatedWith(ATestInterface.class);

        // then
        assertThat(result, is(singleton(ATestClass.class.getDeclaredMethod("aTestMethod"))));
    }

    @SuppressWarnings("unused")
    private class ATestClass {

        @ATestInterface
        private void aTestMethod() {
            throw new UnsupportedOperationException("This is not intended for use.");
        }

        private void secondTestMethod(String asdf) {
            throw new UnsupportedOperationException("This is not intended for use.");
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface ATestInterface {
    }
}
