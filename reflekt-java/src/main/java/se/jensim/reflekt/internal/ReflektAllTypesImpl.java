package se.jensim.reflekt.internal;

import se.jensim.reflekt.ClassFileLocator;
import se.jensim.reflekt.ReflektAllTypes;
import se.jensim.reflekt.ReflektConf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class ReflektAllTypesImpl implements ReflektAllTypes {

    private final boolean includeNestedJars;
    private final List<ClassFileLocator> classFileLocators;
    private final Map<Boolean, Set<String>> keeper = new ConcurrentHashMap<>();

    ReflektAllTypesImpl(ReflektConf conf, List<ClassFileLocator> classFileLocators) {
        this.includeNestedJars = conf.isIncludeNestedJars();
        this.classFileLocators = classFileLocators;
    }

    @Override
    public Set<String> getAllTypes() {
        return keeper.computeIfAbsent(true, b -> initialize());
    }

    private Set<String> initialize() {
        return classFileLocators.stream()
                .flatMap(s -> s.getClasses(includeNestedJars).stream())
                .collect(Collectors.toSet());
    }
}
