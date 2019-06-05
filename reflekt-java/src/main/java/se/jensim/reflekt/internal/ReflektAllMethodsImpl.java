package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toSet;

public class ReflektAllMethodsImpl implements ReflektAllMethods {

    private final Map<Boolean, Set<Method>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllClasses reflektAllClasses;

    ReflektAllMethodsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Method> getAllMethods() {
        return keeper.computeIfAbsent(false, b -> initialize());
    }

    private Set<Method> initialize() {
        return reflektAllClasses.getAllClasses().stream()
                .flatMap(c -> Arrays.stream(c.getMethods()))
                .collect(toSet());
    }
}
