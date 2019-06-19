package io.github.reflekt;

import java.lang.reflect.Constructor;
import java.util.Set;

public interface ReflektConstructorsMatchParams {

    /**
     * Returns all constructors with matching parameter classes
     * @param paramClasses exact parameter classes for desired constructors
     * @return all constructors with matching parameter classes
     * @see java.lang.annotation.Retention
     */
    Set<Constructor> getConstructorsMatchParams(Class... paramClasses);
}
