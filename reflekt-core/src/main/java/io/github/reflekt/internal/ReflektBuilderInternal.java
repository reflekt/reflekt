package io.github.reflekt.internal;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import io.github.reflekt.ClassFileLocator;
import io.github.reflekt.Reflekt;
import io.github.reflekt.ReflektAllConstructors;
import io.github.reflekt.ReflektAllFields;
import io.github.reflekt.ReflektAllMethods;
import io.github.reflekt.ReflektAllTypes;
import io.github.reflekt.ReflektClassesAnnotatedWith;
import io.github.reflekt.ReflektConf;
import io.github.reflekt.ReflektConstructorsAnnotatedWith;
import io.github.reflekt.ReflektConstructorsMatchParams;
import io.github.reflekt.ReflektConstructorsWithAnyParamAnnotated;
import io.github.reflekt.ReflektFieldsAnnotatedWith;
import io.github.reflekt.ReflektMethodsAnnotatedWith;
import io.github.reflekt.ReflektMethodsMatchParams;
import io.github.reflekt.ReflektMethodsReturn;
import io.github.reflekt.ReflektMethodsWithAnyParamAnnotated;
import io.github.reflekt.ReflektSubClasses;

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
        Supplier<ReflektAllClasses> l = LazyBuilder.lazy(() -> new ReflektAllClassesImpl(conf, a));
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
