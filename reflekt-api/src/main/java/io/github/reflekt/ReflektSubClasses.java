package io.github.reflekt;

import java.util.Set;

public interface ReflektSubClasses {

    /**
     * Returns all classes that are sub types of the parameter class
     * @param clazz scanning subclasses for
     * @return all classes that are sub types of the parameter class
     */
    Set<Class> getSubClasses(Class clazz);
}
