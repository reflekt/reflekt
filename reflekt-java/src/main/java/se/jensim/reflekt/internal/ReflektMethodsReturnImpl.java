package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektMethodsReturn;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

class ReflektMethodsReturnImpl implements ReflektMethodsReturn {

    private final Map<Boolean, Map<String, Set<Method>>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllMethods reflektAllMethods;
    private Set<Method> defaultValue = Collections.emptySet();

    ReflektMethodsReturnImpl(ReflektAllMethods reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsReturn(Class clazz) {
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(clazz.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<Method>> init() {
        return reflektAllMethods.getAllMethods().stream().collect(groupingBy(
                m -> m.getReturnType().getCanonicalName(), toSet()));
    }
}
