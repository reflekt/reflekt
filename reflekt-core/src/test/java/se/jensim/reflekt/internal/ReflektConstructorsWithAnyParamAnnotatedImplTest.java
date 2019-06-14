package se.jensim.reflekt.internal;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektConstructorsWithAnyParamAnnotated;

public class ReflektConstructorsWithAnyParamAnnotatedImplTest {

    private ReflektAllConstructors mocka = mock(ReflektAllConstructors.class);
    private final ReflektConstructorsWithAnyParamAnnotated target = new ReflektConstructorsWithAnyParamAnnotatedImpl(lazy(() -> mocka));

    @Test
    public void testGetConstructorsWithAnyParamAnnotated() {
        // given
        Set<Constructor> testConstructors = Arrays.stream(MyTestClass.class.getConstructors()).collect(toSet());
        when(mocka.getAllConstructors()).thenReturn(testConstructors);

        // when
        var result = target.getConstructorsWithAnyParamAnnotated(AnAnnotation.class).stream()
                .map(Constructor::getParameterTypes)
                .map(this::join)
                .collect(toSet());

        // then
        assertThat(result, is(Set.of("[Integer, Boolean]", "[Integer, Short]")));
    }

    private String join(Class[] clazzes){
        return Arrays.stream(clazzes)
                .skip(1)
                .map(Class::getSimpleName)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    private @interface AnAnnotation {
    }

    @SuppressWarnings("unused")
    private class MyTestClass {


        public MyTestClass(Integer i, @AnAnnotation Boolean a) {
        }

        public MyTestClass(Short b) {
        }

        public MyTestClass(@AnAnnotation Integer c, Short s) {
        }

        public MyTestClass() {
        }
    }
}
