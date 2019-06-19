package io.github.reflekt;

import io.github.reflekt.internal.ReflektBuilderInternal;

public interface ReflektBuilder {

    /**
     * Default configured Reflekt instance
     * @return an instance of Reflekt
     * @see io.github.reflekt.ReflektConf
     */
    static Reflekt reflekt(){
        return ReflektBuilderInternal.reflekt();
    }

    /**
     * Shorthand version to get a reflekt instance with package filter set.
     * @param packageFilter Filter the class files found according to this filter, it is used as a starts-with expression
     * @return an instance of Reflekt
     * @see io.github.reflekt.ReflektConf#getPackageFilter()
     */
    static Reflekt reflekt(String packageFilter){
        return ReflektBuilderInternal.reflekt(ReflektConf.builder().setPackageFilter(packageFilter).build());
    }

    /**
     * Configure the reflekt instance according to your needs.
     * @param conf a ReflektConf object for configuring your Reflekt instance
     * @return an instance of Reflekt
     * @see io.github.reflekt.ReflektConf#builder()
     */
    static Reflekt reflekt(ReflektConf conf) {
        return ReflektBuilderInternal.reflekt(conf);
    }
}
