package org.reflekt.internal;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import se.jensim.reflekt.ClassFileLocator;
import se.jensim.reflekt.Reflekt;
import se.jensim.reflekt.ReflektAllConstructors;
import se.jensim.reflekt.ReflektAllFields;
import se.jensim.reflekt.ReflektAllMethods;
import se.jensim.reflekt.ReflektAllTypes;
import se.jensim.reflekt.ReflektClassesAnnotatedWith;
import se.jensim.reflekt.ReflektConf;
import se.jensim.reflekt.ReflektConstructorsAnnotatedWith;
import se.jensim.reflekt.ReflektConstructorsMatchParams;
import se.jensim.reflekt.ReflektConstructorsWithAnyParamAnnotated;
import se.jensim.reflekt.ReflektFieldsAnnotatedWith;
import se.jensim.reflekt.ReflektMethodsAnnotatedWith;
import se.jensim.reflekt.ReflektMethodsMatchParams;
import se.jensim.reflekt.ReflektMethodsReturn;
import se.jensim.reflekt.ReflektMethodsWithAnyParamAnnotated;
import se.jensim.reflekt.ReflektSubClasses;

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
