package io.github.reflekt.internal;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import io.github.reflekt.ReflektAllConstructors;
import io.github.reflekt.ReflektConstructorsMatchParams;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class ReflektConstructorsMatchParamsImpl implements ReflektConstructorsMatchParams {

    private final Supplier<Map<String, Set<Constructor>>> keeper = LazyBuilder.lazy(this::init);
    private final Supplier<ReflektAllConstructors> reflektAllConstructors;
    private final Set<Constructor> defaultValue = Collections.emptySet();

    ReflektConstructorsMatchParamsImpl(Supplier<ReflektAllConstructors> reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsMatchParams(Class... paramClasses) {
        return keeper.get().getOrDefault(bakeParams(paramClasses), defaultValue);
    }

    private Map<String, Set<Constructor>> init() {
        return reflektAllConstructors.get().getAllConstructors().stream()
                .collect(groupingBy(this::bakeParams, toSet()));
    }

    private String bakeParams(Constructor constructor) {
        Class[] parameterTypes = constructor.getParameterTypes();
        if(parameterTypes.length == 0){
            return "[]";
        }
        Class declaredClass = constructor.getDeclaringClass();
        Class host = declaredClass.getEnclosingClass();
        if (!declaredClass.equals(host)) {
            parameterTypes = Arrays.copyOfRange(parameterTypes, 1, parameterTypes.length);
        }
        return bakeParams(parameterTypes);
    }

    private String bakeParams(Class[] paramClasses) {
        return Arrays.stream(paramClasses)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
