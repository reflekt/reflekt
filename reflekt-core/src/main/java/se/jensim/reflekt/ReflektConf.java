package se.jensim.reflekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ReflektConf {

    private final String packageFilter;
    private final boolean includeNestedJars;
    private final List<Supplier<ClassFileLocator>> extraClassFileLocators;

    private ReflektConf(Builder builder) {
        packageFilter = builder.packageFilter;
        includeNestedJars = builder.includeNestedJars;
        extraClassFileLocators = builder.extraClassFileLocators;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Filter the class files found according to this filter, it is used as a starts-with expression
     */
    public String getPackageFilter() {
        return packageFilter;
    }

    /**
     * Do deep scan into nested jars
     */
    public boolean isIncludeNestedJars() {
        return includeNestedJars;
    }

    /**
     * Want to implement your own class file locator, pass it in the conf.
     */
    public List<Supplier<ClassFileLocator>> getExtraClassFileLocators() {
        return extraClassFileLocators;
    }

    public static class Builder {

        private String packageFilter = "";
        private boolean includeNestedJars = false;
        private List<Supplier<ClassFileLocator>> extraClassFileLocators = new ArrayList<>();

        private Builder() {
        }

        /**
         * Filter the class files found according to this filter, it is used as a starts-with expression
         */
        public Builder setPackageFilter(String packageFilter) {
            this.packageFilter = Objects.requireNonNull(packageFilter);
            return this;
        }

        /**
         * Do deep scan into nested jars
         */
        public Builder setIncludeNestedJars(boolean includeNestedJars) {
            this.includeNestedJars = includeNestedJars;
            return this;
        }

        /**
         * Want to implement your own class file locator, pass it in the conf.
         */
        public Builder setExtraClassFileLocators(List<Supplier<ClassFileLocator>> extraClassFileLocators) {
            this.extraClassFileLocators = Objects.requireNonNull(extraClassFileLocators);
            return this;
        }

        public ReflektConf build() {
            return new ReflektConf(this);
        }
    }
}
