package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektConstructorsMatchParams;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

class ReflektConstructorsMatchParamsImpl implements ReflektConstructorsMatchParams {

    private final Map<Boolean, Map<String, Set<Constructor>>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllConstructors reflektAllConstructors;
    private final Set<Constructor> defaultValue = Collections.emptySet();

    ReflektConstructorsMatchParamsImpl(ReflektAllConstructors reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsMatchParams(Class... paramClasses) {
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(bakeParams(paramClasses), defaultValue);
    }

    private Map<String, Set<Constructor>> init() {
        Map<String, Set<Constructor>> collect = reflektAllConstructors.getAllConstructors().stream()
                .collect(groupingBy(this::bakeParams, toSet()));
        return collect;
    }

    private String bakeParams(Constructor constructor) {
        var declaredClass = constructor.getDeclaringClass();
        var host = declaredClass.getNestHost();
        Class[] parameterTypes = constructor.getParameterTypes();
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
