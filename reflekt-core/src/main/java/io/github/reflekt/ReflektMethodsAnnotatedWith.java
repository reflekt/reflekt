package io.github.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsAnnotatedWith {

    Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation);
}
