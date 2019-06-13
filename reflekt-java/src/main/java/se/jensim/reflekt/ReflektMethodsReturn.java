package se.jensim.reflekt;

import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsReturn {

    /**
     * @see java.lang.Void.TYPE
     */
    Set<Method> getMethodsReturn(Class clazz);
}
