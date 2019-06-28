package io.github.reflekt.internal;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.ReflektSubClasses;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class ReflektSubClassesImplTest {

    private final ReflektAllClasses mocka = mock(ReflektAllClasses.class);
    private final ReflektSubClasses target = new ReflektSubClassesImpl(LazyBuilder.lazy(() -> mocka));

    @Test
    public void testGetSubClassesOfObject() {
        // given
        when(mocka.getAllClasses()).thenReturn(new HashSet<>(Arrays.asList(AnAwesomeClass.class, ASuperClass.class)));

        // when
        Set<Class> result = target.getSubClasses(Object.class);

        // then
        assertThat(result, is(new HashSet<>(Arrays.asList(AnAwesomeClass.class, ASuperClass.class))));
    }

    @Test
    public void testGetSubClassesOfMiddle() {
        // given
        when(mocka.getAllClasses()).thenReturn(new HashSet<>(Arrays.asList(AnAwesomeClass.class, ASuperClass.class, AStupidInterface.class)));

        // when
        Set<Class> result = target.getSubClasses(ASuperClass.class);

        // then
        assertThat(result, is(singleton(AnAwesomeClass.class)));
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
