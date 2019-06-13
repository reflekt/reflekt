package se.jensim.reflekt.internal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import org.junit.Test;
import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektMethodsWithAnyParamAnnotated;

public class ReflektMethodsWithAnyParamAnnotatedImplTest {

    private ReflektAllMethods mocka = mock(ReflektAllMethods.class);
    private final ReflektMethodsWithAnyParamAnnotated target = new ReflektMethodsWithAnyParamAnnotatedImpl(lazy(() -> mocka));

    @Test
    public void testGetMethodsWithAnyParamAnnotated() throws NoSuchMethodException {
        // given
        when(mocka.getAllMethods()).thenReturn(Set.of(ATestClass.class.getDeclaredMethods()));

        // when
        var result = target.getMethodsWithAnyParamAnnotated(ATestInterface.class);

        // then
        assertThat(result, is(Set.of(ATestClass.class.getDeclaredMethod("aTestMethod", String.class))));
    }

    @SuppressWarnings("unused")
    private class ATestClass {


        private void aTestMethod(@ATestInterface String param) {
            throw new UnsupportedOperationException("This is not intended for use.");
        }

        private void secondTestMethod(String asdf) {
            throw new UnsupportedOperationException("This is not intended for use.");
        }
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface ATestInterface {
    }
}
