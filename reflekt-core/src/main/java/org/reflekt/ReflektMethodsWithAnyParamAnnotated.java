package org.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsWithAnyParamAnnotated {

    Set<Method> getMethodsWithAnyParamAnnotated(Class<? extends Annotation> annotation);
}
