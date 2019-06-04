package se.jensim.reflekt.internal;

import se.jensim.reflekt.ClassFileLocator;

import java.util.Set;

class ClassFileLocatorJar implements ClassFileLocator {

    private final String packageFilter;

    ClassFileLocatorJar(String packageFilter) {
        this.packageFilter = packageFilter;
    }

    @Override
    public Set<String> getClasses(boolean includeNestedJars) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
