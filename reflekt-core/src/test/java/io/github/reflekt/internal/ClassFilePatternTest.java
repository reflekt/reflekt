package io.github.reflekt.internal;

import static io.github.reflekt.internal.ClassFilePattern.isClassFile;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ClassFilePatternTest {

    @Parameter
    public String filePath;
    @Parameter(1)
    public boolean expectation;

    @Parameters(name = "{index}: path({0})={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"/io/github/reflekt/internal/ReflektAbstractAny$ParamAnnotated$1.class", false},
                {"io/github/reflekt/internal/ReflektAbstractAny$ParamAnnotated$1.class", false},
                {"/io/github/reflekt/internal/ReflektAbstractAny$ParamAnnotated.class", true},
                {"io/github/reflekt/internal/ReflektAbstractAny$ParamAnnotated.class", true}
        });
    }

    @Test(timeout = 500)
    public void evaluate() {
        assertThat(isClassFile(filePath), is(expectation));
    }
}
