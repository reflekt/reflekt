package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektConstructorsMatchParams;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class ReflektConstructorsMatchParamsImpl implements ReflektConstructorsMatchParams {

    private final Map<Boolean, Map<String, Set<Constructor>>> keeper = new ConcurrentHashMap<>();
    private Set<Constructor> defaultValue = Collections.emptySet();

    @Override

    public Set<Constructor> getConstructorsMatchParams(Class... paramClasses) {
        var strList = Arrays.stream(paramClasses)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", ", "[", "]"));
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(strList, defaultValue);
    }

    private Map<String, Set<Constructor>> init() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
