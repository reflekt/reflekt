package se.jensim.reflekt.internal;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Test;
import se.jensim.reflekt.ReflektSubClasses;

public class ReflektSubClassesImplTest {

    private final ReflektAllClasses mocka = mock(ReflektAllClasses.class);
    private final ReflektSubClasses target = new ReflektSubClassesImpl(lazy(() -> mocka));

    @Test
    public void testGetSubClassesOfObject() {
        // given
        when(mocka.getAllClasses()).thenReturn(Set.of(AnAwesomeClass.class, ASuperClass.class));

        // when
        var result = target.getSubClasses(Object.class);

        // then
        assertThat(result, Matchers.is(Set.of(AnAwesomeClass.class, ASuperClass.class)));
    }

    @Test
    public void testGetSubClassesOfMiddle() {
        // given
        when(mocka.getAllClasses()).thenReturn(Set.of(AnAwesomeClass.class, ASuperClass.class, AStupidInterface.class));

        // when
        var result = target.getSubClasses(ASuperClass.class);

        // then
        assertThat(result, Matchers.is(Set.of(AnAwesomeClass.class)));
    }


    @SuppressWarnings("unused")
    private interface AStupidInterface {
    }

    @SuppressWarnings("unused")
    private class AnAwesomeClass extends ASuperClass {
    }

    private class ASuperClass implements AStupidInterface {
    }
}
