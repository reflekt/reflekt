package se.jensim.reflekt.internal;

import java.util.Objects;
import java.util.function.Supplier;

interface LazyBuilder {

    static <P> Lazy<P> lazy(Supplier<P> supplier) {
        return new Lazy<>(supplier);
    }

    class Lazy<T> implements Supplier<T> {

        private final Supplier<T> supplier;
        private T ref = null;
        private final Object lock = new Object();

        Lazy(Supplier<T> supplier) {
            this.supplier = Objects.requireNonNull(supplier);
        }

        @Override
        public T get() {
            synchronized (lock) {
                if (ref == null) {
                    ref = supplier.get();
                }
                return ref;
            }
        }
    }
}
