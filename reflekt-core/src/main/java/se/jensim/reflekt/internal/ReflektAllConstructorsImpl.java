package se.jensim.reflekt.internal;

import static se.jensim.reflekt.internal.LazyBuilder.lazy;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import se.jensim.reflekt.ReflektAllConstructors;

public class ReflektAllConstructorsImpl implements ReflektAllConstructors {

    private final Supplier<Set<Constructor>> keeper = lazy(this::initialize);
    private final Supplier<ReflektAllClasses> reflektAllClasses;

    ReflektAllConstructorsImpl(Supplier<ReflektAllClasses> reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Constructor> getAllConstructors() {
        return keeper.get();
    }

    private Set<Constructor> initialize() {
        return reflektAllClasses.get().getAllClasses().stream()
                .flatMap(c -> Arrays.stream(c.getDeclaredConstructors()))
                .collect(Collectors.toSet());
    }
}
