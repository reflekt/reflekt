package io.github.reflekt.internal;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import io.github.reflekt.ReflektAllMethods;
import io.github.reflekt.ReflektMethodsMatchParams;
import org.hamcrest.Matchers;
import org.junit.Test;

@SuppressWarnings("unused")
public class ReflektMethodsMatchParamsImplTest {

    private ReflektAllMethods mocka = mock(ReflektAllMethods.class);
    private final ReflektMethodsMatchParams target = new ReflektMethodsMatchParamsImpl(LazyBuilder.lazy(() -> mocka));

    @Test
    public void testGetMethodsMatchParams() throws NoSuchMethodException {
        // given
        when(mocka.getAllMethods()).thenReturn(Set.of(ATestClass.class.getDeclaredMethods()));

        // when
        var result = target.getMethodsMatchParams(String.class);

        // then
        assertThat(result, Matchers.is(Set.of(ATestClass.class.getDeclaredMethod("aTestMethod", String.class))));
    }

    private class ATestClass {

        private void aTestMethod(String param) {
            throw new UnsupportedOperationException("This is not intended for use.");
        }

        private String secondTestMethod(Object asdf) {
            throw new UnsupportedOperationException("This is not intended for use.");
        }
    }
}
