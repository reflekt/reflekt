package se.jensim.reflekt;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface ReflektClassesAnnotatedWith {

    Set<Class> getClassesAnnotatedWith(Class<Annotation> annotation);
}
