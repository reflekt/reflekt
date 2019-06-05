package se.jensim.reflekt.internal;

import org.junit.Test;
import se.jensim.reflekt.ReflektAllMethods;

import java.lang.reflect.Method;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReflektAllMethodsImplTest {

    private ReflektAllClasses m = mock(ReflektAllClasses.class);
    private final ReflektAllMethods target = new ReflektAllMethodsImpl(m);

    @Test
    public void testGetAllMethods() {
        // given
        when(m.getAllClasses()).thenReturn(singleton(ReflektAllMethodsImplTest.class));

        // when
        var fields = target.getAllMethods()
                .stream().map(Method::getName)
                .collect(toSet());

        // then
        assertTrue(fields.contains("testGetAllMethods"));
    }
}
