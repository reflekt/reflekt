package se.jensim.reflekt;

public class ReflektConf {

    private final String packageFilter;

    private ReflektConf(Builder builder) {
        packageFilter = builder.packageFilter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPackageFilter() {
        return packageFilter;
    }

    public static class Builder {

        private String packageFilter = "";

        private Builder() {
        }

        public Builder setPackageFilter(String packageFilter) {
            this.packageFilter = packageFilter;
            return this;
        }

        public ReflektConf build() {
            return new ReflektConf(this);
        }
    }
}
