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
        locatorList.addAll(conf.getExtraClassFileLocators());

        var a = new ReflektAllTypesImpl(conf, locatorList);
        var l = new ReflektAllClassesImpl(a);
        var k = new ReflektSubClassesImpl(l);
        var b = new ReflektClassesAnnotatedWithImpl(l);
        var m = new ReflektAllConstructorsImpl(l);
        var c = new ReflektConstructorsAnnotatedWithImpl(m);
        var d = new ReflektConstructorsMatchParamsImpl(m);
        var e = new ReflektConstructorsWithAnyParamAnnotatedImpl(m);
        var o = new ReflektAllFieldsImpl(l);
        var f = new ReflektFieldsAnnotatedWithImpl(o);
        var n = new ReflektAllMethodsImpl(l);
        var g = new ReflektMethodsAnnotatedWithImpl(n);
        var h = new ReflektMethodsMatchParamsImpl(n);
        var i = new ReflektMethodsReturnImpl(n);
        var j = new ReflektMethodsWithAnyParamAnnotatedImpl(n);

        return new ReflektImpl(m, o, n, a, b, c, d, e, f, g, h, i, j, k);
    }
}
