package se.jensim.reflekt.internal;

import se.jensim.reflekt.ClassFileLocator;
import se.jensim.reflekt.ReflektAllTypes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class ReflektAllTypesImpl implements ReflektAllTypes {

    private final List<ClassFileLocator> classFileLocators;

    ReflektAllTypesImpl(List<ClassFileLocator> classFileLocators) {
        this.classFileLocators = classFileLocators;
    }

    @Override
    public Set<String> getAllTypes() {
        return classFileLocators.stream()
                .flatMap(s -> s.getClasses(true).stream())
                .collect(Collectors.toSet());
    }
}
