package io.github.reflekt;

import java.util.Set;

public interface ClassFileLocator {

    /**
     * Returns class references ready to load with Class#forName
     * @param includeNestedJars weather or not to look in nested jars for class files
     * @return class references ready to load with Class#forName(String)
     * @see Class#forName(String)
     */
    Set<String> getClasses(boolean includeNestedJars);
}
