package se.jensim.reflekt.internal;

import static se.jensim.reflekt.internal.LazyBuilder.lazy;

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
        Supplier<ClassFileLocator> jarLocator = lazy(() -> new ClassFileLocatorJar(conf));
        Supplier<ClassFileLocator> classPathLocator = lazy(() -> new ClassFileLocatorClassPath(conf));
        List<Supplier<ClassFileLocator>> locatorList = Arrays.asList(jarLocator, classPathLocator);
        locatorList.addAll(conf.getExtraClassFileLocators());

        Supplier<ReflektAllTypes> a = lazy(() -> new ReflektAllTypesImpl(conf, locatorList));
        Supplier<ReflektAllClasses> l = lazy(() -> new ReflektAllClassesImpl(a));
        Supplier<ReflektSubClasses> k = lazy(() -> new ReflektSubClassesImpl(l));
        Supplier<ReflektClassesAnnotatedWith> b = lazy(() -> new ReflektClassesAnnotatedWithImpl(l));
        Supplier<ReflektAllConstructors> m = lazy(() -> new ReflektAllConstructorsImpl(l));
        Supplier<ReflektConstructorsAnnotatedWith> c = lazy(() -> new ReflektConstructorsAnnotatedWithImpl(m));
        Supplier<ReflektConstructorsMatchParams> d = lazy(() -> new ReflektConstructorsMatchParamsImpl(m));
        Supplier<ReflektConstructorsWithAnyParamAnnotated> e = lazy(() -> new ReflektConstructorsWithAnyParamAnnotatedImpl(m));
        Supplier<ReflektAllFields> o = lazy(() -> new ReflektAllFieldsImpl(l));
        Supplier<ReflektFieldsAnnotatedWith> f = lazy(() -> new ReflektFieldsAnnotatedWithImpl(o));
        Supplier<ReflektAllMethods> n = lazy(() -> new ReflektAllMethodsImpl(l));
        Supplier<ReflektMethodsAnnotatedWith> g = lazy(() -> new ReflektMethodsAnnotatedWithImpl(n));
        Supplier<ReflektMethodsMatchParams> h = lazy(() -> new ReflektMethodsMatchParamsImpl(n));
        Supplier<ReflektMethodsReturn> i = lazy(() -> new ReflektMethodsReturnImpl(n));
        Supplier<ReflektMethodsWithAnyParamAnnotated> j = lazy(() -> new ReflektMethodsWithAnyParamAnnotatedImpl(n));

        return new ReflektImpl(m, o, n, a, b, c, d, e, f, g, h, i, j, k);
    }
}
