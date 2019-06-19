package io.github.reflekt;

import java.util.Set;

public interface ReflektAllTypes {

    /**
     * Returns all types, can be used with Class#forName(String)
     * @return all types
     * @see Class#forName(String)
     */
    Set<String> getAllTypes();
}
