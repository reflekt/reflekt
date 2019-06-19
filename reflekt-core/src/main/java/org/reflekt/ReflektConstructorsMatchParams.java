package org.reflekt;

import java.lang.reflect.Constructor;
import java.util.Set;

public interface ReflektConstructorsMatchParams {

    Set<Constructor> getConstructorsMatchParams(Class... paramClasses);
}
