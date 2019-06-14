package se.jensim.reflekt.internal;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;
import se.jensim.reflekt.ReflektConf;

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
