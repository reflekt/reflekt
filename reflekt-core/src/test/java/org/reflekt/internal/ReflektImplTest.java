package org.reflekt.internal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektAllFields;
import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektAllTypes;
import se.jensim.reflekt.ReflektClassesAnnotatedWith;
import se.jensim.reflekt.ReflektConstructorsAnnotatedWith;
import se.jensim.reflekt.ReflektConstructorsMatchParams;
import se.jensim.reflekt.ReflektConstructorsWithAnyParamAnnotated;
import se.jensim.reflekt.ReflektFieldsAnnotatedWith;
import se.jensim.reflekt.ReflektMethodsAnnotatedWith;
import se.jensim.reflekt.ReflektMethodsMatchParams;
import se.jensim.reflekt.ReflektMethodsReturn;
import se.jensim.reflekt.ReflektMethodsWithAnyParamAnnotated;
import se.jensim.reflekt.ReflektSubClasses;

public class ReflektImplTest {

    private final ReflektAllConstructors a = mock(ReflektAllConstructors.class);
    private final ReflektAllFields b = mock(ReflektAllFields.class);
    private final ReflektAllMethods c = mock(ReflektAllMethods.class);
    private final ReflektAllTypes d = mock(ReflektAllTypes.class);
    private final ReflektClassesAnnotatedWith e = mock(ReflektClassesAnnotatedWith.class);
    private final ReflektConstructorsAnnotatedWith f = mock(ReflektConstructorsAnnotatedWith.class);
    private final ReflektConstructorsMatchParams g = mock(ReflektConstructorsMatchParams.class);
    private final ReflektConstructorsWithAnyParamAnnotated h = mock(ReflektConstructorsWithAnyParamAnnotated.class);
    private final ReflektFieldsAnnotatedWith i = mock(ReflektFieldsAnnotatedWith.class);
    private final ReflektMethodsAnnotatedWith j = mock(ReflektMethodsAnnotatedWith.class);
    private final ReflektMethodsMatchParams k = mock(ReflektMethodsMatchParams.class);
    private final ReflektMethodsReturn l = mock(ReflektMethodsReturn.class);
    private final ReflektMethodsWithAnyParamAnnotated m = mock(ReflektMethodsWithAnyParamAnnotated.class);
    private final ReflektSubClasses n = mock(ReflektSubClasses.class);
    private final ReflektImpl target = new ReflektImpl(
            LazyBuilder.lazy(() -> a),
            LazyBuilder.lazy(() -> b),
            LazyBuilder.lazy(() -> c),
            LazyBuilder.lazy(() -> d),
            LazyBuilder.lazy(() -> e),
            LazyBuilder.lazy(() -> f),
            LazyBuilder.lazy(() -> g),
            LazyBuilder.lazy(() -> h),
            LazyBuilder.lazy(() -> i),
            LazyBuilder.lazy(() -> j),
            LazyBuilder.lazy(() -> k),
            LazyBuilder.lazy(() -> l),
            LazyBuilder.lazy(() -> m),
            LazyBuilder.lazy(() -> n));

    @Before
    public void setUp() {
        Mockito.reset(a, b, c, d, e, f, g, h, i, j, k, l, m, n);
        when(a.getAllConstructors()).thenReturn(Collections.emptySet());
        when(b.getAllFields()).thenReturn(Collections.emptySet());
        when(c.getAllMethods()).thenReturn(Collections.emptySet());
        when(d.getAllTypes()).thenReturn(Collections.emptySet());
        when(e.getClassesAnnotatedWith(any())).thenReturn(Collections.emptySet());
        when(f.getConstructorsAnnotatedWith(any())).thenReturn(Collections.emptySet());
        when(g.getConstructorsMatchParams(anyVararg())).thenReturn(Collections.emptySet());
        when(h.getConstructorsWithAnyParamAnnotated(anyVararg())).thenReturn(Collections.emptySet());
        when(i.getFieldsAnnotatedWith(any())).thenReturn(Collections.emptySet());
        when(j.getMethodsAnnotatedWith(any())).thenReturn(Collections.emptySet());
        when(k.getMethodsMatchParams(anyVararg())).thenReturn(Collections.emptySet());
        when(l.getMethodsReturn(any())).thenReturn(Collections.emptySet());
        when(m.getMethodsWithAnyParamAnnotated(any())).thenReturn(Collections.emptySet());
        when(n.getSubClasses(any())).thenReturn(Collections.emptySet());
    }

    @Test
    public void testGetAllConstructors() {
        // given

        // when
        target.getAllConstructors();

        // then
        verify(a, times(1)).getAllConstructors();
        verifyZeroInteractions(b, c, d, e, f, g, h, i, j, k, l, m, n);
    }

    @Test
    public void testGetAllFields() {
        // given

        // when
        target.getAllFields();

        // then
        verify(b, times(1)).getAllFields();
        verifyZeroInteractions(a, c, d, e, f, g, h, i, j, k, l, m, n);
    }

    @Test
    public void testGetAllMethods() {
        // given

        // when
        target.getAllMethods();

        // then
        verify(c, times(1)).getAllMethods();
        verifyZeroInteractions(a, b, d, e, f, g, h, i, j, k, l, m, n);
    }


    @Test
    public void testGetAllTypes() {
        // given

        // when
        target.getAllTypes();

        // then
        verify(d, times(1)).getAllTypes();
        verifyZeroInteractions(a, b, c, e, f, g, h, i, j, k, l, m, n);
    }

    @Test
    public void testGetClassesAnnotatedWith() {
        // given

        // when
        target.getClassesAnnotatedWith(Annotation.class);

        // then
        verify(e, times(1)).getClassesAnnotatedWith(any());
        verifyZeroInteractions(a, b, c, d, f, g, h, i, j, k, l, m, n);
    }

    @Test
    public void testGetConstructorsAnnotatedWith() {
        // given

        // when
        target.getConstructorsAnnotatedWith(Annotation.class);

        // then
        verify(f, times(1)).getConstructorsAnnotatedWith(any());
        verifyZeroInteractions(a, b, c, d, e, g, h, i, j, k, l, m, n);
    }

    @Test
    public void testGetConstructorsMatchParams() {
        // given

        // when
        target.getConstructorsMatchParams();

        // then
        verify(g, times(1)).getConstructorsMatchParams();
        verifyZeroInteractions(a, b, c, d, e, f, h, i, j, k, l, m, n);
    }

    @Test
    public void testGetConstructorsWithAnyParamAnnotated() {
        // given

        // when
        target.getConstructorsWithAnyParamAnnotated(Annotation.class);

        // then
        verify(h, times(1)).getConstructorsWithAnyParamAnnotated(any());
        verifyZeroInteractions(a, b, c, d, e, f, g, i, j, k, l, m, n);
    }

    @Test
    public void testGetFieldsAnnotatedWith() {
        // given

        // when
        target.getFieldsAnnotatedWith(Annotation.class);

        // then
        verify(i, times(1)).getFieldsAnnotatedWith(any());
        verifyZeroInteractions(a, b, c, d, e, f, g, h, j, k, l, m, n);
    }

    @Test
    public void testGetMethodsAnnotatedWith() {
        // given

        // when
        target.getMethodsAnnotatedWith(Annotation.class);

        // then
        verify(j, times(1)).getMethodsAnnotatedWith(any());
        verifyZeroInteractions(a, b, c, d, e, f, g, h, i, k, l, m, n);
    }

    @Test
    public void testGetMethodsMatchParams() {
        // given

        // when
        target.getMethodsMatchParams();

        // then
        verify(k, times(1)).getMethodsMatchParams();
        verifyZeroInteractions(a, b, c, d, e, f, g, h, i, j, l, m, n);
    }

    @Test
    public void testGetMethodsReturn() {
        // given

        // when
        target.getMethodsReturn(Object.class);

        // then
        verify(l, times(1)).getMethodsReturn(any());
        verifyZeroInteractions(a, b, c, d, e, f, g, h, i, j, k, m, n);
    }

    @Test
    public void testGetMethodsWithAnyParamAnnotated() {
        // given

        // when
        target.getMethodsWithAnyParamAnnotated(Annotation.class);

        // then
        verify(m, times(1)).getMethodsWithAnyParamAnnotated(any());
        verifyZeroInteractions(a, b, c, d, e, f, g, h, i, j, k, l, n);
    }

    @Test
    public void testGetSubClasses() {
        // given

        // when
        target.getSubClasses(Object.class);

        // then
        verify(n, times(1)).getSubClasses(any());
        verifyZeroInteractions(a, b, c, d, e, f, g, h, i, j, k, l, m);
    }
}
