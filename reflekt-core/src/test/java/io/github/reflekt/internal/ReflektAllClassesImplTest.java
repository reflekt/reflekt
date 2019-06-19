package io.github.reflekt.internal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import io.github.reflekt.ReflektAllTypes;
import org.junit.Test;

public class ReflektAllClassesImplTest {

    private ReflektAllTypes mocka = mock(ReflektAllTypes.class);
    private final ReflektAllClasses target = new ReflektAllClassesImpl(LazyBuilder.lazy(() -> mocka));

    @Test
    public void getAllClasses() {
        // given
        when(mocka.getAllTypes()).thenReturn(Set.of(this.getClass().getCanonicalName()));

        // when
        var allClasses = target.getAllClasses();

        // then
        assertThat(allClasses, equalTo(Set.of(this.getClass())));
    }
}
