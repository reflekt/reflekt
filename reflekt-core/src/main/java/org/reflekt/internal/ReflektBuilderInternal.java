package org.reflekt.internal;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.reflekt.ClassFileLocator;
import org.reflekt.Reflekt;
import org.reflekt.ReflektAllConstructors;
import org.reflekt.ReflektAllFields;
import org.reflekt.ReflektAllMethods;
import org.reflekt.ReflektAllTypes;
import org.reflekt.ReflektClassesAnnotatedWith;
import org.reflekt.ReflektConf;
import org.reflekt.ReflektConstructorsAnnotatedWith;
import org.reflekt.ReflektConstructorsMatchParams;
import org.reflekt.ReflektConstructorsWithAnyParamAnnotated;
import org.reflekt.ReflektFieldsAnnotatedWith;
import org.reflekt.ReflektMethodsAnnotatedWith;
import org.reflekt.ReflektMethodsMatchParams;
import org.reflekt.ReflektMethodsReturn;
import org.reflekt.ReflektMethodsWithAnyParamAnnotated;
import org.reflekt.ReflektSubClasses;

public interface ReflektBuilderInternal {

    static Reflekt reflekt() {
        return reflekt(ReflektConf.builder().build());
    }

    static Reflekt reflekt(ReflektConf conf) {
        Supplier<ClassFileLocator> jarLocator = LazyBuilder.lazy(() -> new ClassFileLocatorJar(conf));
        Supplier<ClassFileLocator> classPathLocator = LazyBuilder.lazy(() -> new ClassFileLocatorClassPath(conf));
        List<Supplier<ClassFileLocator>> locatorList = Arrays.asList(jarLocator, classPathLocator);
        locatorList.addAll(conf.getExtraClassFileLocators());

        Supplier<ReflektAllTypes> a = LazyBuilder.lazy(() -> new ReflektAllTypesImpl(conf, locatorList));
        Supplier<ReflektAllClasses> l = LazyBuilder.lazy(() -> new ReflektAllClassesImpl(a));
        Supplier<ReflektSubClasses> k = LazyBuilder.lazy(() -> new ReflektSubClassesImpl(l));
        Supplier<ReflektClassesAnnotatedWith> b = LazyBuilder.lazy(() -> new ReflektClassesAnnotatedWithImpl(l));
        Supplier<ReflektAllConstructors> m = LazyBuilder.lazy(() -> new ReflektAllConstructorsImpl(l));
        Supplier<ReflektConstructorsAnnotatedWith> c = LazyBuilder.lazy(() -> new ReflektConstructorsAnnotatedWithImpl(m));
        Supplier<ReflektConstructorsMatchParams> d = LazyBuilder.lazy(() -> new ReflektConstructorsMatchParamsImpl(m));
        Supplier<ReflektConstructorsWithAnyParamAnnotated> e = LazyBuilder.lazy(() -> new ReflektConstructorsWithAnyParamAnnotatedImpl(m));
        Supplier<ReflektAllFields> o = LazyBuilder.lazy(() -> new ReflektAllFieldsImpl(l));
        Supplier<ReflektFieldsAnnotatedWith> f = LazyBuilder.lazy(() -> new ReflektFieldsAnnotatedWithImpl(o));
        Supplier<ReflektAllMethods> n = LazyBuilder.lazy(() -> new ReflektAllMethodsImpl(l));
        Supplier<ReflektMethodsAnnotatedWith> g = LazyBuilder.lazy(() -> new ReflektMethodsAnnotatedWithImpl(n));
        Supplier<ReflektMethodsMatchParams> h = LazyBuilder.lazy(() -> new ReflektMethodsMatchParamsImpl(n));
        Supplier<ReflektMethodsReturn> i = LazyBuilder.lazy(() -> new ReflektMethodsReturnImpl(n));
        Supplier<ReflektMethodsWithAnyParamAnnotated> j = LazyBuilder.lazy(() -> new ReflektMethodsWithAnyParamAnnotatedImpl(n));

        return new ReflektImpl(m, o, n, a, b, c, d, e, f, g, h, i, j, k);
    }
}
