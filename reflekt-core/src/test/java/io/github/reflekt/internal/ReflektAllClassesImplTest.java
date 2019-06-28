package io.github.reflekt.internal;

import static io.github.reflekt.ReflektBuilder.reflekt;
import static io.github.reflekt.internal.LazyBuilder.lazy;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.reflekt.Reflekt;
import io.github.reflekt.ReflektAllTypes;
import io.github.reflekt.ReflektConf;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
        List<String> list = new ArrayList<>();
        list.add(tmp.getRoot().getPath());
        conf = ReflektConf.builder()
                .setClassResourceDirs(list)
                .build();
        target = new ReflektAllClassesImpl(conf, lazy(() -> mocka));
    }

    @Test
    public void getAllClasses() {
        // given
        Set<String> set = singleton(this.getClass().getCanonicalName());
        when(mocka.getAllTypes()).thenReturn(set);

        // when
        Set<Class> allClasses = target.getAllClasses();

        // then
        assertThat(allClasses, equalTo(singleton(this.getClass())));
    }

    @Test
    public void useOverridePath() throws Exception {
        // given
        String[] pkg = {"com", "example"};
        String className = "FindMe";
        String clazz = String.join(".", pkg) + "." + className + ".class";
        File newFolder = tmp.newFolder(pkg);
        URL resource = this.getClass().getClassLoader().getResource("FindMe.class");
        assertNotNull(resource);
        Path fileResource = new File(resource.toURI()).toPath();
        Files.copy(fileResource, new File(newFolder, "FindMe.class").toPath());
        ReflektConf conf = ReflektConf.builder()
                .setClassResourceDirs(singletonList(tmp.getRoot().getPath()))
                .build();
        Reflekt reflekt = reflekt(conf);

        // when
        Set<String> allTypes = reflekt.getSubClasses(Object.class).stream() // Ensure class loading works
                .map(Class::getCanonicalName)
                .collect(toSet());

        // then
        Assert.assertThat(allTypes, is(singleton(clazz.substring(0, clazz.length() - 6))));
    }
}
