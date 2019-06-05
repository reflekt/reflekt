package se.jensim.reflekt.internal;

import org.junit.Test;
import se.jensim.reflekt.ReflektAllTypes;

import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReflektAllClassesImplTest {

    private ReflektAllTypes mockReflektAllTypes = mock(ReflektAllTypes.class);
    private final ReflektAllClasses target = new ReflektAllClassesImpl(mockReflektAllTypes);

    @Test
    public void getAllClasses() {
        // given
        when(mockReflektAllTypes.getAllTypes()).thenReturn(Set.of(this.getClass().getCanonicalName()));

        // when
        var allClasses = target.getAllClasses();

        // then
        assertThat(allClasses, equalTo(Set.of(this.getClass())));
    }
}
