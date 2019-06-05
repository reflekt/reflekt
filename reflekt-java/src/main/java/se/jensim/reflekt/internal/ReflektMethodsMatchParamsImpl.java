package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektMethodsMatchParams;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class ReflektMethodsMatchParamsImpl implements ReflektMethodsMatchParams {

    private final Map<Boolean, Map<String, Set<Method>>> keeper = new ConcurrentHashMap<>();
    private Set<Method> defaultValue = Collections.emptySet();

    @Override
    public Set<Method> getMethodsMatchParams(Class... paramClasses) {
        var strList = Arrays.stream(paramClasses)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", ","[","]"));
        return keeper.computeIfAbsent(false, b -> init()).getOrDefault(strList, defaultValue);
    }

    private Map<String, Set<Method>> init() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
