package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllConstructors;

import java.lang.reflect.Constructor;
import java.util.Set;

public class ReflektAllConstructorsImpl implements ReflektAllConstructors {

    private final ReflektAllClasses reflektAllClasses;

    ReflektAllConstructorsImpl(ReflektAllClasses reflektAllClasses) {
        this.reflektAllClasses = reflektAllClasses;
    }

    @Override
    public Set<Constructor> getAllConstructors() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
