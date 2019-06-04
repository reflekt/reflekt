package se.jensim.reflekt.internal;

import se.jensim.reflekt.*;

import java.util.Arrays;
import java.util.List;

public interface ReflektBuilderInternal {

    static Reflekt reflekt() {
        return reflekt(ReflektConf.builder().build());
    }

    static Reflekt reflekt(ReflektConf conf) {
        String packageFilter = conf.getPackageFilter();
        ClassFileLocator jarLocator = new ClassFileLocatorJar(packageFilter);
        ClassFileLocator classPathLocator = new ClassFileLocatorClassPath(packageFilter);
        List<ClassFileLocator> locatorList = Arrays.asList(jarLocator, classPathLocator);
        ReflektAllTypes a = new ReflektAllTypesImpl(locatorList);
        ReflektSubClasses k = new ReflektSubClassesImpl(a);
        ReflektClassesAnnotatedWith b = new ReflektClassesAnnotatedWithImpl();
        ReflektConstructorsAnnotatedWith c = new ReflektConstructorsAnnotatedWithImpl();
        ReflektConstructorsMatchParams d = new ReflektConstructorsMatchParamsImpl();
        ReflektConstructorsWithAnyParamAnnotated e = new ReflektConstructorsWithAnyParamAnnotatedImpl();
        ReflektFieldsAnnotatedWith f = new ReflektFieldsAnnotatedWithImpl();
        ReflektMethodsAnnotatedWith g = new ReflektMethodsAnnotatedWithImpl();
        ReflektMethodsMatchParams h = new ReflektMethodsMatchParamsImpl();
        ReflektMethodsReturn i = new ReflektMethodsReturnImpl();
        ReflektMethodsWithAnyParamAnnotated j = new ReflektMethodsWithAnyParamAnnotatedImpl();
        return new ReflektImpl(a, b, c, d, e, f, g, h, i, j, k);
    }
}
