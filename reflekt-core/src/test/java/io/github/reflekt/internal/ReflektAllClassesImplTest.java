package io.github.reflekt.internal;

import static io.github.reflekt.ReflektBuilder.reflekt;
import static io.github.reflekt.internal.LazyBuilder.lazy;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.Reflekt;
import io.github.reflekt.ReflektAllTypes;
import io.github.reflekt.ReflektConf;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

public class ReflektAllClassesImplTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    private ReflektAllTypes mocka = mock(ReflektAllTypes.class);
    private ReflektConf conf;
    private ReflektAllClasses target;

    @Before
    public void setUp() {
        Mockito.reset(mocka);
        conf = ReflektConf.builder()
                .setClassResourceDirs(List.of(tmp.getRoot().getPath()))
                .build();
        target = new ReflektAllClassesImpl(conf, lazy(() -> mocka));
    }

    @Test
    public void getAllClasses() {
        // given
        when(mocka.getAllTypes()).thenReturn(Set.of(this.getClass().getCanonicalName()));

        // when
        var allClasses = target.getAllClasses();

        // then
        assertThat(allClasses, equalTo(Set.of(this.getClass())));
    }

    @Test
    public void useOverridePath() throws Exception {
        // given
        String[] pkg = {"com", "example"};
        String className = "HelloWorld";
        String clazz = String.join(".", pkg) + "." + className + ".class";
        File newFolder = tmp.newFolder(pkg);
        String fileRef = String.join(File.separator, pkg) + File.separator + className + ".class";
        URL resource = this.getClass().getClassLoader().getResource("HelloWorld.class");
        Files.copy(Path.of(resource.toURI()), new File(newFolder, "HelloWorld.class").toPath());
        ReflektConf conf = ReflektConf.builder().setClassResourceDirs(List.of(tmp.getRoot().getPath())).build();
        Reflekt reflekt = reflekt(conf);

        // when
        Set<String> allTypes = reflekt.getSubClasses(Object.class).stream() // Ensure class loading works
                .map(Class::getCanonicalName)
                .collect(toSet());

        // then
        Assert.assertThat(allTypes, is(Set.of(clazz.substring(0, clazz.length() - 6))));
    }
}
