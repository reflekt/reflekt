package se.jensim.reflekt.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

import java.util.function.Supplier;

import org.junit.Test;

public class LazyBuilderTest {

    @Test
    public void baseSupplierIsOnlyCalledOnce() {
        // given
        Supplier<String> supplier = mock(Supplier.class);
        when(supplier.get()).thenReturn("hello");
        Supplier<String> lazySupplier = lazy(supplier);

        // when
        lazySupplier.get();
        lazySupplier.get();

        // then
        verify(supplier, times(1)).get();
    }
}
