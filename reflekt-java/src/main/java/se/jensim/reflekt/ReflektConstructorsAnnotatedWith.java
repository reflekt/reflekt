package se.jensim.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

public interface ReflektConstructorsAnnotatedWith {

    Set<Constructor> getConstructorsAnnotatedWith(Class<? extends Annotation> annotation);
}
