package io.github.reflekt.internal;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import io.github.reflekt.ReflektAllMethods;
import io.github.reflekt.ReflektMethodsReturn;

class ReflektMethodsReturnImpl implements ReflektMethodsReturn {

    private final Supplier<Map<String, Set<Method>>> keeper = LazyBuilder.lazy(this::init);
    private final Supplier<ReflektAllMethods> reflektAllMethods;
    private Set<Method> defaultValue = Collections.emptySet();

    ReflektMethodsReturnImpl(Supplier<ReflektAllMethods> reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsReturn(Class clazz) {
        return keeper.get().getOrDefault(clazz.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<Method>> init() {
        return reflektAllMethods.get().getAllMethods().stream().collect(groupingBy(
                m -> m.getReturnType().getCanonicalName(), toSet()));
    }
}
