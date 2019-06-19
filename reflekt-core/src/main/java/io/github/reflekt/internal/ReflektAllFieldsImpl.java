package io.github.reflekt.internal;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Supplier;

import io.github.reflekt.ReflektAllFields;

public class ReflektAllFieldsImpl implements ReflektAllFields {

    private final Supplier<Set<Field>> keeper = LazyBuilder.lazy(this::initialize);
    private final Supplier<ReflektAllClasses> reflektAllClasses;

    ReflektAllFieldsImpl(Supplier<ReflektAllClasses> reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Field> getAllFields() {
        return keeper.get();
    }

    private Set<Field> initialize() {
        return reflektAllClasses.get().getAllClasses().stream()
                .flatMap(c -> stream(c.getFields()))
                .collect(toSet());
    }
}
