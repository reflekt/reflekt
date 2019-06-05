package se.jensim.reflekt.internal;

import se.jensim.reflekt.Reflekt;
import se.jensim.reflekt.ReflektConf;

import java.util.Arrays;

public interface ReflektBuilderInternal {

    static Reflekt reflekt() {
        return reflekt(ReflektConf.builder().build());
    }

    static Reflekt reflekt(ReflektConf conf) {
        var jarLocator = new ClassFileLocatorJar(conf);
        var classPathLocator = new ClassFileLocatorClassPath(conf);
        var locatorList = Arrays.asList(jarLocator, classPathLocator);
        var a = new ReflektAllTypesImpl(conf, locatorList);
        var k = new ReflektSubClassesImpl(a);
        var l = new ReflektAllClassesImpl(a);
        var b = new ReflektClassesAnnotatedWithImpl(l);
        var c = new ReflektConstructorsAnnotatedWithImpl();
        var d = new ReflektConstructorsMatchParamsImpl();
        var e = new ReflektConstructorsWithAnyParamAnnotatedImpl();
        var f = new ReflektFieldsAnnotatedWithImpl();
        var g = new ReflektMethodsAnnotatedWithImpl();
        var h = new ReflektMethodsMatchParamsImpl();
        var i = new ReflektMethodsReturnImpl();
        var j = new ReflektMethodsWithAnyParamAnnotatedImpl();
        return new ReflektImpl(a, b, c, d, e, f, g, h, i, j, k);
    }
}
