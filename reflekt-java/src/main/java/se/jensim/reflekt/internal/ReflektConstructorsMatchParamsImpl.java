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

class ReflektConstructorsMatchParamsImpl implements ReflektConstructorsMatchParams {

    private final Map<Boolean, Map<String, Set<Constructor>>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllConstructors reflektAllConstructors;
    private Set<Constructor> defaultValue = Collections.emptySet();

    ReflektConstructorsMatchParamsImpl(ReflektAllConstructors reflektAllConstructors) {
        this.reflektAllConstructors = reflektAllConstructors;
    }

    @Override
    public Set<Constructor> getConstructorsMatchParams(Class... paramClasses) {
        var strList = Arrays.stream(paramClasses)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", ", "[", "]"));
        return keeper.computeIfAbsent(false, b -> init())
                .getOrDefault(strList, defaultValue);
    }

    private Map<String, Set<Constructor>> init() {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO
}
}
