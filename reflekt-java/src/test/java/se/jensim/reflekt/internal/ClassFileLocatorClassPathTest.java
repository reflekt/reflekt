package se.jensim.reflekt.internal;

import org.junit.Test;
import se.jensim.reflekt.ReflektConf;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ClassFileLocatorClassPathTest {

    private final ReflektConf conf = ReflektConf.builder().setPackageFilter("se.jensim.reflekt").build();
    private final ClassFileLocatorClassPath target = new ClassFileLocatorClassPath(conf);

    @Test
    public void getClasses() {
        // given

        // when
        Set<String> classes = target.getClasses(true);

        // then
        assertThat(classes, is(not(empty())));
    }
}
