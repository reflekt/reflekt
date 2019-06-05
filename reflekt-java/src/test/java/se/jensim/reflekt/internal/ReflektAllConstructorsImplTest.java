package se.jensim.reflekt.internal;

import org.junit.Test;
import se.jensim.reflekt.ReflektAllConstructors;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReflektAllConstructorsImplTest {

    private ReflektAllClasses m = mock(ReflektAllClasses.class);
    private ReflektAllConstructors target = new ReflektAllConstructorsImpl(m);

    @Test
    public void testGetAllConstructors() {
        // given
        when(m.getAllClasses()).thenReturn(Collections.singleton(ATestClassToDiscover.class));

        // when
        var constructors = target.getAllConstructors();

        // then
        assertThat(constructors, hasSize(4));
    }

    @SuppressWarnings("unused")
    private class ATestClassToDiscover {

        public ATestClassToDiscover(String a) {
        }

        public ATestClassToDiscover(Boolean b) {
        }

        public ATestClassToDiscover(Integer c) {
        }

        public ATestClassToDiscover() {
        }
    }
}


