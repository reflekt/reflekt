package io.github.reflekt.internal;

import static io.github.reflekt.ReflektBuilder.reflekt;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import io.github.reflekt.Reflekt;
import io.github.reflekt.ReflektConf;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ClassFileLocatorClassPathTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Test
    public void getClasses() {
        // given
        ReflektConf conf = ReflektConf.builder().setPackageFilter("io.github.reflekt").build();
        ClassFileLocatorClassPath target = new ClassFileLocatorClassPath(conf);

        // when
        Set<String> classes = target.getClasses(true);

        // then
        assertTrue(classes.contains(this.getClass().getCanonicalName()));
    }

    @Test
    public void unableToGetStackOverflowFromDeepPackages() throws IOException {
        // given
        ReflektConf conf = ReflektConf.builder().setPackageFilter("io.github.reflekt").build();
        ClassFileLocatorClassPath target = new ClassFileLocatorClassPath(conf);
        String classfile = createDeepDir();

        // when
        Set<String> result = target.initialize(Stream.of(tmp.getRoot()));

        // then
        assertTrue(result.contains(classfile));
    }

    /**
     * 416 levels of packages, if i counted correctly.
     *
     * @return class name (canonical) of the created class file
     */
    private String createDeepDir() throws IOException {
        String alphabeth = "a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.";
        for (int i = 0; i < 4; i++) {
            alphabeth += alphabeth;
        }
        alphabeth = "io.github.reflekt." + alphabeth;
        String[] a = alphabeth.split("\\.");
        try {
            tmp.newFolder(a);
        } catch (IOException e) {
            //Nothing
        }
        String join = String.join(File.separator, a) + File.separator + "MyClass.class";
        tmp.newFile(join);
        return join.replace(File.separatorChar, '.').substring(0, join.length() - 6);
    }

    @Test
    public void useOverridePath() throws IOException {
        // given
        String[] pkg = {"com", "example", "test", "find", "me", "please"};
        String className = "PrettyPlease";
        String clazz = String.join(".", pkg) + "." + className + ".class";
        tmp.newFolder(pkg);
        String fileRef = String.join(File.separator, pkg) + File.separator + className + ".class";
        tmp.newFile(fileRef);
        ReflektConf conf = ReflektConf.builder().setClassResourceDirs(singletonList(tmp.getRoot().getPath())).build();
        Reflekt reflekt = reflekt(conf);

        // when
        Set<String> allTypes = reflekt.getAllTypes();

        // then
        Assert.assertThat(allTypes, is(singleton(clazz.substring(0, clazz.length() - 6))));
    }
}
