package se.jensim.reflekt.internal;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ClassFileLocatorJarTest {

    private final ClassFileLocatorJar target = new ClassFileLocatorJar(packageFilter);

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
