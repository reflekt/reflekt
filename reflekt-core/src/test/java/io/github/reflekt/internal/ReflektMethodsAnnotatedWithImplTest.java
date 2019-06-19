package io.github.reflekt.internal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import io.github.reflekt.ReflektAllMethods;
import io.github.reflekt.ReflektMethodsAnnotatedWith;
import org.junit.Test;

public class ReflektMethodsAnnotatedWithImplTest {

    private ReflektAllMethods mocka = mock(ReflektAllMethods.class);
    private final ReflektMethodsAnnotatedWith target = new ReflektMethodsAnnotatedWithImpl(LazyBuilder.lazy(() -> mocka));

    @Test
    public void testGetMethodsAnnotatedWith() throws NoSuchMethodException {
        // given
        when(mocka.getAllMethods()).thenReturn(Set.of(ATestClass.class.getDeclaredMethods()));

        // when
        var result = target.getMethodsAnnotatedWith(ATestInterface.class);

        // then
        assertThat(result, is(Set.of(ATestClass.class.getDeclaredMethod("aTestMethod"))));
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
