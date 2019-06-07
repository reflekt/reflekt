package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllFields;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

public class ReflektAllFieldsImpl implements ReflektAllFields {

    private final Supplier<Set<Field>> keeper = lazy(this::initialize);
    private final ReflektAllClasses reflektAllClasses;

    ReflektAllFieldsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Field> getAllFields() {
        return keeper.get();
    }

    private Set<Field> initialize() {
        return reflektAllClasses.getAllClasses().stream()
                .flatMap(c -> stream(c.getFields()))
                .collect(toSet());
    }
}
