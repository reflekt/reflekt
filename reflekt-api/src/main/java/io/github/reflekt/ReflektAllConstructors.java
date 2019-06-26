package io.github.reflekt;

import java.lang.reflect.Constructor;
import java.util.Set;

public interface ReflektAllConstructors {

    /**
     * Returns all constructors
     * @return all constructors
     */
    Set<Constructor> getAllConstructors();
}
