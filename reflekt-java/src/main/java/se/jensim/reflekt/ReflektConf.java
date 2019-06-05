package se.jensim.reflekt;

public class ReflektConf {

    private final String packageFilter;
    private final boolean includeNestedJars;

    private ReflektConf(Builder builder) {
        packageFilter = builder.packageFilter;
        includeNestedJars = builder.includeNestedJars;
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

    public static class Builder {

        private String packageFilter = "";
        private boolean includeNestedJars = false;

        private Builder() {
        }

        /**
         * Filter the class files found according to this filter, it is used as a starts-with expression
         */
        public Builder setPackageFilter(String packageFilter) {
            this.packageFilter = packageFilter;
            return this;
        }

        /**
         * Do deep scan into nested jars
         */
        public Builder setIncludeNestedJars(boolean includeNestedJars) {
            this.includeNestedJars = includeNestedJars;
            return this;
        }

        public ReflektConf build() {
            return new ReflektConf(this);
        }
    }
}
