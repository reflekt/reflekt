package com.example;

import org.junit.Test;
import se.jensim.reflekt.RefleKt;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static se.jensim.RefleKt.refleKt;

public class ReflektTest {

    @Test
    public void createInstance() {
        // given
        RefleKt reflekt = refleKt();

        // when
        Set<String> allTypes = reflekt.getAllTypes();

        // then
        assertNotNull(allTypes);
        assertThat(allTypes, is(not(empty())));
        assertTrue(allTypes.contains(ReflektTest.class.getCanonicalName()));
    }
}
