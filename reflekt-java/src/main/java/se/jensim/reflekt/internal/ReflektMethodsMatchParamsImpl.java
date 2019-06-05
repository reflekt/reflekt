package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektMethodsMatchParams;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class ReflektMethodsMatchParamsImpl implements ReflektMethodsMatchParams {

    private final Map<Boolean, Map<String, Set<Method>>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllMethods reflektAllMethods;
    private Set<Method> defaultValue = Collections.emptySet();

    ReflektMethodsMatchParamsImpl(ReflektAllMethods reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsMatchParams(Class... paramClasses) {
        var strList = Arrays.stream(paramClasses)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", ","[","]"));
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(strList, defaultValue);
    }

    private Map<String, Set<Method>> init() {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO
}
}
