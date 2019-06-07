package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektMethodsMatchParams;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

class ReflektMethodsMatchParamsImpl implements ReflektMethodsMatchParams {

    private final Supplier<Map<String, Set<Method>>> keeper = lazy(this::init);
    private final ReflektAllMethods reflektAllMethods;
    private Set<Method> defaultValue = Collections.emptySet();

    ReflektMethodsMatchParamsImpl(ReflektAllMethods reflektAllMethods) {
        this.reflektAllMethods = reflektAllMethods;
    }

    @Override
    public Set<Method> getMethodsMatchParams(Class... paramClasses) {
        return keeper.get().getOrDefault(bakeParams(paramClasses), defaultValue);
    }

    private Map<String, Set<Method>> init() {
        return reflektAllMethods.getAllMethods().stream().collect(groupingBy(
                (Method m) -> bakeParams(m.getParameterTypes()), toSet()));
    }

    private String bakeParams(Class[] paramClasses) {
        return Arrays.stream(paramClasses)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
