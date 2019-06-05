package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllConstructors;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ReflektAllConstructorsImpl implements ReflektAllConstructors {

    private final Map<Boolean, Set<Constructor>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllClasses reflektAllClasses;

    ReflektAllConstructorsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Constructor> getAllConstructors() {
        return keeper.computeIfAbsent(false, b -> initialize());
    }

    private Set<Constructor> initialize() {
        return reflektAllClasses.getAllClasses().stream()
                .flatMap(c -> Arrays.stream(c.getConstructors()))
                .collect(Collectors.toSet());
    }
}
