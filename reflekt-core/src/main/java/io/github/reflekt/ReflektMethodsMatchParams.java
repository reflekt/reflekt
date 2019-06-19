package io.github.reflekt;

import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsMatchParams {

    /**
     * Returns all methods with matching parameter classes
     * @param paramClasses exact parameter classes for desired methods
     * @return all methods with matching parameter classes
     * @see java.lang.annotation.Retention
     */
    Set<Method> getMethodsMatchParams(Class... paramClasses);
}
