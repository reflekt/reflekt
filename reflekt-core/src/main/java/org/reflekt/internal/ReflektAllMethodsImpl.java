package org.reflekt.internal;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Supplier;

import se.jensim.reflekt.ReflektAllMethods;

public class ReflektAllMethodsImpl implements ReflektAllMethods {

    private final Supplier<Set<Method>> keeper = LazyBuilder.lazy(this::initialize);
    private final Supplier<ReflektAllClasses> reflektAllClasses;

    ReflektAllMethodsImpl(Supplier<ReflektAllClasses> reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Method> getAllMethods() {
        return keeper.get();
    }

    private Set<Method> initialize() {
        return reflektAllClasses.get().getAllClasses().stream()
                .flatMap(c -> stream(c.getMethods()))
                .collect(toSet());
    }
}
