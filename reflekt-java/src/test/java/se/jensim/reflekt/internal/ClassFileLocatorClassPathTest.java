package se.jensim.reflekt.internal;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ClassFileLocatorClassPathTest {

    private final ClassFileLocatorClassPath target = new ClassFileLocatorClassPath(packageFilter);

    @Test
    public void getClasses() {
        // given

        // when
        Set<String> classes = target.getClasses(true);
        fail("Not yet implemented");

        // then
        assertThat(classes, is(not(empty())));
    }
}
