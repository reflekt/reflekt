package se.jensim.reflekt;

import se.jensim.reflekt.internal.ReflektBuilderInternal;

public interface ReflektBuilder {

    static Reflekt reflekt(){
        return ReflektBuilderInternal.reflekt();
    }

    static Reflekt reflekt(ReflektConf conf) {
        return ReflektBuilderInternal.reflekt(conf);
    }
}
