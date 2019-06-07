package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllConstructors;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static se.jensim.reflekt.internal.LazyBuilder.lazy;

public class ReflektAllConstructorsImpl implements ReflektAllConstructors {

    private final Supplier<Set<Constructor>> keeper = lazy(this::initialize);
    private final ReflektAllClasses reflektAllClasses;

    ReflektAllConstructorsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Constructor> getAllConstructors() {
        return keeper.get();
    }

    private Set<Constructor> initialize() {
        return reflektAllClasses.getAllClasses().stream()
                .flatMap(c -> Arrays.stream(c.getDeclaredConstructors()))
                .collect(Collectors.toSet());
    }
}
