package org.reflekt;

import org.reflekt.internal.ReflektBuilderInternal;

public interface ReflektBuilder {

    /**
     * Default configured Reflekt instance
     * @see ReflektConf
     */
    static Reflekt reflekt(){
        return ReflektBuilderInternal.reflekt();
    }

    /**
     * Shorthand version to get a reflekt instance with package filter set.
     * @see ReflektConf#getPackageFilter()
     */
    static Reflekt reflekt(String packageFilter){
        return ReflektBuilderInternal.reflekt(ReflektConf.builder().setPackageFilter(packageFilter).build());
    }

    /**
     * Configure the reflekt instance according to your needs.
     * @see org.reflekt.ReflektConf#builder()
     */
    static Reflekt reflekt(ReflektConf conf) {
        return ReflektBuilderInternal.reflekt(conf);
    }
}
