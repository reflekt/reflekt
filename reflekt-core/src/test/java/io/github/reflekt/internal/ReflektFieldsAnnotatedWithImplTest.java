package io.github.reflekt.internal;

import static java.util.Collections.singleton;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.ReflektAllFields;
import io.github.reflekt.ReflektFieldsAnnotatedWith;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.Test;

@SuppressWarnings("unused")
public class ReflektFieldsAnnotatedWithImplTest {

    private ReflektAllFields mocka = mock(ReflektAllFields.class);
    private final ReflektFieldsAnnotatedWith target = new ReflektFieldsAnnotatedWithImpl(LazyBuilder.lazy(() -> mocka));

    @Test
    public void testGetFieldsAnnotatedWith() throws NoSuchFieldException {
        // given
        when(mocka.getAllFields()).thenReturn(new HashSet<>(Arrays.asList(ATestClass.class.getDeclaredFields())));

        // when
        Set<Field> result = target.getFieldsAnnotatedWith(ATestAnnotation.class);

        // then
        assertThat(result, Matchers.is(singleton(ATestClass.class.getField("bar"))));
    }

    private class ATestClass {
        public volatile String foo;
        @SuppressWarnings("WeakerAccess")
        @ATestAnnotation
        public volatile String bar;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface ATestAnnotation {
    }
}
