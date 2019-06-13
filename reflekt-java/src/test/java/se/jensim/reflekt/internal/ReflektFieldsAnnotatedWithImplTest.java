package se.jensim.reflekt.internal;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Test;
import se.jensim.reflekt.ReflektAllFields;
import se.jensim.reflekt.ReflektFieldsAnnotatedWith;

@SuppressWarnings("unused")
public class ReflektFieldsAnnotatedWithImplTest {

    private ReflektAllFields mocka = mock(ReflektAllFields.class);
    private final ReflektFieldsAnnotatedWith target = new ReflektFieldsAnnotatedWithImpl(lazy(() -> mocka));

    @Test
    public void testGetFieldsAnnotatedWith() throws NoSuchFieldException {
        // given
        when(mocka.getAllFields()).thenReturn(Set.of(ATestClass.class.getDeclaredFields()));

        // when
        Set<Field> result = target.getFieldsAnnotatedWith(ATestAnnotation.class);

        // then
        assertThat(result, Matchers.is(Set.of(ATestClass.class.getField("bar"))));
    }

    private class ATestClass {
        public volatile String foo;
        @ATestAnnotation
        public volatile String bar;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface ATestAnnotation {
    }
}
