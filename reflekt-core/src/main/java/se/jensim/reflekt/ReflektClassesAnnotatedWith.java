package se.jensim.reflekt;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface ReflektClassesAnnotatedWith {

    /**
     * Only Runtime-visible annotations are discoverable
     * @see java.lang.annotation.Retention
     */
    Set<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation);
}
