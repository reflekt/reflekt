package se.jensim.reflekt.internal;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

interface LazyBuilder {

    static <P> Lazy<P> lazy(Supplier<P> supplier) {
        return new Lazy<>(supplier);
    }

    class Lazy<T> implements Supplier<T> {

        private final Supplier<T> supplier;
        private final AtomicReference<T> ref = new AtomicReference<>(null);

        Lazy(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T get() {
            synchronized (ref){
                var get = ref.get();
                if(get != null){
                    return get;
                }
                return ref.updateAndGet(v -> v == null ? supplier.get() : v);
            }
        }
    }
}
