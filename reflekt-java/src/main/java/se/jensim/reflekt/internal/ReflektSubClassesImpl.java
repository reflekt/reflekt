package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllTypes;
import se.jensim.reflekt.ReflektSubClasses;

import java.util.Set;

class ReflektSubClassesImpl implements ReflektSubClasses {

    private final ReflektAllTypes reflektAllTypes;

    public ReflektSubClassesImpl(ReflektAllTypes reflektAllTypes) {
        this.reflektAllTypes = reflektAllTypes;
    }

    @Override
    public Set<Class> getSubClasses(Class clazz) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
