package io.github.reflekt.internal;

import static java.util.Collections.singleton;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.ClassFileLocator;
import io.github.reflekt.ReflektAllTypes;
import io.github.reflekt.ReflektConf;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import org.hamcrest.Matchers;
import org.junit.Test;

@SuppressWarnings("unused")
public class ReflektAllTypesImplTest {

    private ReflektConf conf = ReflektConf.builder().build();
    private ClassFileLocator mocka = mock(ClassFileLocator.class);
    private ClassFileLocator mockb = mock(ClassFileLocator.class);
    private ClassFileLocator mockc = mock(ClassFileLocator.class);
    private List<Supplier<ClassFileLocator>> locators = Arrays.asList(
            LazyBuilder.lazy(() -> mocka),
            LazyBuilder.lazy(() -> mockb),
            LazyBuilder.lazy(() -> mockc)
    );
    private final ReflektAllTypes target = new ReflektAllTypesImpl(conf, locators);

    @Test
    public void testGetAllTypes() {
        // given
        when(mocka.getClasses(anyBoolean())).thenReturn(singleton("mockA"));
        when(mockb.getClasses(anyBoolean())).thenReturn(singleton("mockB"));
        when(mockc.getClasses(anyBoolean())).thenReturn(singleton("mockC"));

        // when
        Set<String> result = target.getAllTypes();

        // then
        assertThat(result, Matchers.is(new HashSet<>(Arrays.asList("mockA", "mockB", "mockC"))));
    }
}
