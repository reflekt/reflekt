package io.github.reflekt.internal;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.ReflektClassesAnnotatedWith;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class ReflektClassesAnnotatedWithImplTest {

    private ReflektAllClasses mocka = mock(ReflektAllClasses.class);
    private final ReflektClassesAnnotatedWith target = new ReflektClassesAnnotatedWithImpl(LazyBuilder.lazy(() -> mocka));

    @Test
    public void testGetClassesAnnotatedWith() {
        // given
        when(mocka.getAllClasses()).thenReturn(new HashSet<>(Arrays.asList(NoAnnotation.class, WithAnnotation.class)));

        // when
        Set<Class> annotatedClasses = target.getClassesAnnotatedWith(MyAnnotation.class);

        // then
        assertThat(annotatedClasses, equalTo(singleton(WithAnnotation.class)));
    }

    private class NoAnnotation {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface MyAnnotation {
    }

    @MyAnnotation
    private class WithAnnotation {
    }
}
