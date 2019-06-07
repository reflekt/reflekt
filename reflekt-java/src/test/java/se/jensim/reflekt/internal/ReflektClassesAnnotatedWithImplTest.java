package se.jensim.reflekt.internal;

import org.junit.Test;
import se.jensim.reflekt.ReflektClassesAnnotatedWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReflektClassesAnnotatedWithImplTest {

    private ReflektAllClasses mocka = mock(ReflektAllClasses.class);
    private final ReflektClassesAnnotatedWith target = new ReflektClassesAnnotatedWithImpl(mocka);

    @Test
    public void testGetClassesAnnotatedWith() {
        // given
        when(mocka.getAllClasses()).thenReturn(Set.of(NoAnnotation.class, WithAnnotation.class));

        // when
        var annotatedClasses = target.getClassesAnnotatedWith(MyAnnotation.class);

        // then
        assertThat(annotatedClasses, equalTo(Set.of(WithAnnotation.class)));
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
