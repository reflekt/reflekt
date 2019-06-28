package io.github.reflekt.internal;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.ReflektAllConstructors;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Set;
import org.junit.Test;

public class ReflektAllConstructorsImplTest {

    private ReflektAllClasses m = mock(ReflektAllClasses.class);
    private ReflektAllConstructors target = new ReflektAllConstructorsImpl(LazyBuilder.lazy(() -> m));

    @Test
    public void testGetAllConstructors() {
        // given
        when(m.getAllClasses()).thenReturn(Collections.singleton(ATestClassToDiscover.class));

        // when
        Set<Constructor> constructors = target.getAllConstructors();

        // then
        assertThat(constructors, hasSize(4));
    }

    @SuppressWarnings("unused")
    private class ATestClassToDiscover {

        ATestClassToDiscover(String a) {
        }

        ATestClassToDiscover(Boolean b) {
        }

        ATestClassToDiscover(Integer c) {
        }

        ATestClassToDiscover() {
        }
    }
}


