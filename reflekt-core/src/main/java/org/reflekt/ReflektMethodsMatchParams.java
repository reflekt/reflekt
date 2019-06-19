package org.reflekt;

import java.lang.reflect.Method;
import java.util.Set;

public interface ReflektMethodsMatchParams {

    Set<Method> getMethodsMatchParams(Class... paramClasses);
}
