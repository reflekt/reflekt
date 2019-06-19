package io.github.reflekt.internal;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import io.github.reflekt.ReflektAllMethods;
import io.github.reflekt.ReflektMethodsMatchParams;

class ReflektMethodsMatchParamsImpl implements ReflektMethodsMatchParams {

    private final Supplier<Map<String, Set<Method>>> keeper = LazyBuilder.lazy(this::init);
    private final Supplier<ReflektAllMethods> reflektAllMethods;
    private Set<Method> defaultValue = Collections.emptySet();

    ReflektMethodsMatchParamsImpl(Supplier<ReflektAllMethods> reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsMatchParams(Class... paramClasses) {
        return keeper.get().getOrDefault(bakeParams(paramClasses), defaultValue);
    }

    private Map<String, Set<Method>> init() {
        return reflektAllMethods.get().getAllMethods().stream().collect(groupingBy(
                (Method m) -> bakeParams(m.getParameterTypes()), toSet()));
    }

    private String bakeParams(Class[] paramClasses) {
        return Arrays.stream(paramClasses)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
