package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllFields;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toSet;

public class ReflektAllFieldsImpl implements ReflektAllFields {

    private final Map<Boolean, Set<Field>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllClasses reflektAllClasses;

     ReflektAllFieldsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Field> getAllFields() {
        return keeper.computeIfAbsent(false, b -> initialize());
    }

    private Set<Field> initialize() {
        return reflektAllClasses.getAllClasses().stream()
                .flatMap(c -> Arrays.stream(c.getFields()))
                .collect(toSet());
    }
}
