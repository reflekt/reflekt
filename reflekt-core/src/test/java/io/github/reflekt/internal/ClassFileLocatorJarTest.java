package io.github.reflekt.internal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ClassFileLocatorJarTest {

    @Rule
    public TemporaryFolder tmpDir = new TemporaryFolder();

    private static final String PACKAGE_FILTER = "io.github.reflekt";
    private static final String GOOD_CLASS_FILE = PACKAGE_FILTER.replace('.', '/') + "/example/test/Foo$Bar.class";

    @Test
    public void getClasses() throws IOException {
        // given
        File dir = tmpDir.getRoot();
        File file = new File(dir, "MyJar.jar");
        try (ZipOutputStream os = new ZipOutputStream(new FileOutputStream(file))) {
            os.putNextEntry(new ZipEntry(GOOD_CLASS_FILE));
            os.write("I am a class file, i promise".getBytes());
            os.putNextEntry(new ZipEntry("com/example/BooFar.class"));
            os.write("I am a class file, but im not wanted".getBytes());
        }

        // when
        Set<String> classes = ClassFileLocatorJar.getClassesFromNestedJars(file.toURI(), PACKAGE_FILTER, false);

        // then
        assertThat(classes, equalTo(Collections.singleton("io.github.reflekt.example.test.Foo.Bar")));
    }
}
