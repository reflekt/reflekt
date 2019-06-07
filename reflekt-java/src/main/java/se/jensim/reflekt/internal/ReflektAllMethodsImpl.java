package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

public class ReflektAllMethodsImpl implements ReflektAllMethods {

    private final Supplier<Set<Method>> keeper = lazy(this::initialize);
    private final ReflektAllClasses reflektAllClasses;

    ReflektAllMethodsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Method> getAllMethods() {
        return keeper.get();
    }

    private Set<Method> initialize() {
        return reflektAllClasses.getAllClasses().stream()
                .flatMap(c -> stream(c.getMethods()))
                .collect(toSet());
    }
}
