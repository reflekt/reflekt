package se.jensim.reflekt.internal;

import se.jensim.reflekt.ClassFileLocator;

import java.util.Set;

public class ClassFileLocatorClassPath implements ClassFileLocator {

    private final String packageFilter;

    ClassFileLocatorClassPath(String packageFilter) {
        this.packageFilter = packageFilter;
    }

    @Override
    public Set<String> getClasses(boolean includeNestedJars) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
