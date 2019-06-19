package io.github.reflekt;

import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsReturn {

    /**
     * Returns all methods with matching return type
     * <p><code>java.lang.Void.TYPE</code> for void returns
     * @return all methods with matching return type
     * @param clazz return type scanned for
     */
    Set<Method> getMethodsReturn(Class clazz);
}
