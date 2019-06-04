package se.jensim.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsAnnotatedWith {

    Set<Method> getMethodsAnnotatedWith(Class<Annotation> annotation);
}
