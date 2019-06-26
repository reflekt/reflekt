package io.github.reflekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ReflektConf {

    private final String packageFilter;
    private final boolean includeNestedJars;
    private final List<Supplier<ClassFileLocator>> extraClassFileLocators;
    private final List<String> classResourceDirs;

    private ReflektConf(Builder builder) {
        packageFilter = builder.packageFilter;
        includeNestedJars = builder.includeNestedJars;
        extraClassFileLocators = builder.extraClassFileLocators;
        classResourceDirs = builder.classResourceDirs;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Filter the class files found according to this filter, it is used as a starts-with expression
     * @return a starts-with expression for filtering classes
     */
    public String getPackageFilter() {
        return packageFilter;
    }

    /**
     * Do deep scan into nested jars
     * @return do deep scan into nested jars
     */
    public boolean isIncludeNestedJars() {
        return includeNestedJars;
    }

    /**
     * Want to implement your own class file locator, pass it in the conf.
     * @return all extra ClassFileLocators
     */
    public List<Supplier<ClassFileLocator>> getExtraClassFileLocators() {
        return extraClassFileLocators;
    }

    /**
     * Returns the override value (paths) to use in locating class files
     * @return the override value (paths) to use in locating class files
     */
    public List<String> getClassResourceDirs() {
        return classResourceDirs;
    }

    public static class Builder {

        private List<String> classResourceDirs = null;
        private String packageFilter = "";
        private boolean includeNestedJars = false;
        private List<Supplier<ClassFileLocator>> extraClassFileLocators = new ArrayList<>();

        private Builder() {
        }

        /**
         * Filter the class files found according to this filter, it is used as a starts-with expression.
         * This vastly improves performance.
         * @return the builder
         * @param packageFilter a starts-with expression for filtering classes found
         */
        public Builder setPackageFilter(String packageFilter) {
            this.packageFilter = Objects.requireNonNull(packageFilter);
            return this;
        }

        /**
         * Do deep scan into nested jars
         * @return the builder
         * @param includeNestedJars go through nested jars looking for class files
         */
        public Builder setIncludeNestedJars(boolean includeNestedJars) {
            this.includeNestedJars = includeNestedJars;
            return this;
        }

        /**
         * Want to implement your own class file locator, pass it in the conf.
         * @return the builder
         * @param extraClassFileLocators extra ClassFileLocators to use in Reflekt
         * @see io.github.reflekt.Reflekt
         * @see io.github.reflekt.ClassFileLocator
         */
        public Builder setExtraClassFileLocators(List<Supplier<ClassFileLocator>> extraClassFileLocators) {
            this.extraClassFileLocators = Objects.requireNonNull(extraClassFileLocators);
            return this;
        }

        /**
         * The override value (paths) to use in locating class files
         * @param classResourceDirs The override value (paths) to use in locating class files
         * @return the builder
         */
        public Builder setClassResourceDirs(List<String> classResourceDirs) {
            this.classResourceDirs = classResourceDirs;
            return this;
        }

        public ReflektConf build() {
            return new ReflektConf(this);
        }
    }
}
