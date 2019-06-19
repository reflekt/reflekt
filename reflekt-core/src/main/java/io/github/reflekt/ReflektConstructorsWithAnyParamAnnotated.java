package io.github.reflekt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

public interface ReflektConstructorsWithAnyParamAnnotated {

    Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation);
}
