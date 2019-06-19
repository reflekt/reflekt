package org.reflekt;

import java.util.Set;

public interface ClassFileLocator {

    /**
     * Returns class references ready to load with Class#forName
     * @param includeNestedJars
     * @return class references ready to load with Class#forName(String)
     * @see Class#forName(String)
     */
    Set<String> getClasses(boolean includeNestedJars);
}
