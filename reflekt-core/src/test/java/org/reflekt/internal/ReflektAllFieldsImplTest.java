package org.reflekt.internal;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Test;
import se.jensim.reflekt.ReflektAllFields;

public class ReflektAllFieldsImplTest {

    public String aStringToFind = "Find me!";
    private ReflektAllClasses m = mock(ReflektAllClasses.class);
    private final ReflektAllFields target = new ReflektAllFieldsImpl(LazyBuilder.lazy(() -> m));

    @Test
    public void testGetAllFields() {
        // given
        when(m.getAllClasses()).thenReturn(singleton(ReflektAllFieldsImplTest.class));

        // when
        var fields = target.getAllFields()
                .stream().map(Field::getName)
                .collect(toSet());

        // then
        assertThat(fields, equalTo(Set.of("aStringToFind")));
    }
}
