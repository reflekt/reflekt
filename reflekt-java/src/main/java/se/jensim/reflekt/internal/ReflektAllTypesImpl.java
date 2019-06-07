package se.jensim.reflekt.internal;

import se.jensim.reflekt.ClassFileLocator;
import se.jensim.reflekt.ReflektAllTypes;
import se.jensim.reflekt.ReflektConf;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toSet;
import static se.jensim.reflekt.internal.LazyBuilder.lazy;

class ReflektAllTypesImpl implements ReflektAllTypes {

    private final boolean includeNestedJars;
    private final List<ClassFileLocator> classFileLocators;
    private final Supplier<Set<String>> keeper = lazy(this::initialize);

    ReflektAllTypesImpl(ReflektConf conf, List<ClassFileLocator> classFileLocators) {
        this.includeNestedJars = conf.isIncludeNestedJars();
        this.classFileLocators = classFileLocators;
    }

    @Override
    public Set<String> getAllTypes() {
        return keeper.get();
    }

    private Set<String> initialize() {
        return classFileLocators.stream()
                .flatMap(s -> s.getClasses(includeNestedJars).stream())
                .collect(toSet());
    }
}
