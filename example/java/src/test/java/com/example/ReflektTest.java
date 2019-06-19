package com.example;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.reflekt.ReflektBuilder.reflekt;

import java.util.Set;

import org.junit.Test;
import org.reflekt.Reflekt;

public class ReflektTest {

    @Test
    public void createInstance() {
        // given
        Reflekt reflekt = reflekt();

        // when
        Set<String> allTypes = reflekt.getAllTypes();

        // then
        assertNotNull(allTypes);
        assertThat(allTypes, is(not(empty())));
        assertTrue(allTypes.contains(ReflektTest.class.getCanonicalName()));
    }
}
