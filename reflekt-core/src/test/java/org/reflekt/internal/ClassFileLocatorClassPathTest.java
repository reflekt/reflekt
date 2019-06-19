package org.reflekt.internal;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import se.jensim.reflekt.ReflektConf;

public class ClassFileLocatorClassPathTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Test
    public void getClasses() {
        // given
        ReflektConf conf = ReflektConf.builder().setPackageFilter("se.jensim.reflekt").build();
        ClassFileLocatorClassPath target = new ClassFileLocatorClassPath(conf);

        // when
        Set<String> classes = target.getClasses(true);

        // then
        assertTrue(classes.contains(this.getClass().getCanonicalName()));
    }

    @Test
    public void unableToGetStackOverflowFromDeepPackages() throws IOException {
        // given
        ReflektConf conf = ReflektConf.builder().setPackageFilter("se.jensim.reflekt").build();
        ClassFileLocatorClassPath target = new ClassFileLocatorClassPath(conf);
        String classfile = createDeepDir();

        // when
        var result = target.initialize(List.of(tmp.getRoot()).stream());

        // then
        assertTrue(result.contains(classfile));
    }

    /**
     * 416 levels of packages, if i counted correctly.
     * @return class name (canonical) of the created class file
     */
    private String createDeepDir() throws IOException {
        String alphabeth = "a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.";
        for (int i = 0; i < 4; i++) {
            alphabeth += alphabeth;
        }
        alphabeth = "se.jensim.reflekt." + alphabeth;
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
}
