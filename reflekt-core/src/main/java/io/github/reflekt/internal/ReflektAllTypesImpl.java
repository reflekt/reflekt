package io.github.reflekt.internal;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import io.github.reflekt.ClassFileLocator;
import io.github.reflekt.ReflektAllTypes;
import io.github.reflekt.ReflektConf;

class ReflektAllTypesImpl implements ReflektAllTypes {

    private final boolean includeNestedJars;
    private final List<Supplier<ClassFileLocator>> classFileLocators;
    private final Supplier<Set<String>> keeper = LazyBuilder.lazy(this::initialize);

    ReflektAllTypesImpl(ReflektConf conf, List<Supplier<ClassFileLocator>> classFileLocators) {
        this.includeNestedJars = conf.isIncludeNestedJars();
        this.classFileLocators = classFileLocators;
    }

    @Override
    public Set<String> getAllTypes() {
        return keeper.get();
    }

    private Set<String> initialize() {

        return classFileLocators.stream().parallel()
                .flatMap(s -> s.get().getClasses(includeNestedJars).stream())
                .collect(toSet());
    }
}
