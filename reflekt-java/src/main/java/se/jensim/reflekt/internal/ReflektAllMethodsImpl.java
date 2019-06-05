package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllMethods;

import java.lang.reflect.Method;
import java.util.Set;

public class ReflektAllMethodsImpl implements ReflektAllMethods {

    private final ReflektAllClasses reflektAllClasses;

    ReflektAllMethodsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Method> getAllMethods() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
