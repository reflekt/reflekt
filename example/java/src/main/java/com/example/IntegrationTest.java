package com.example;

import io.github.reflekt.Reflekt;
import io.github.reflekt.ReflektBuilder;

import java.util.Set;
import java.util.stream.Collectors;

public class IntegrationTest {

    public static void main(String[] args) {
        // given
        Reflekt reflekt = ReflektBuilder.reflekt();
        Set<String> expectedTypes = Set.of(MyInnerClass.class).stream()
                .map(Class::getCanonicalName)
                .collect(Collectors.toSet());

        // when
        Set<String> allTypes = reflekt.getAllTypes();

        // then
        var found = allTypes.containsAll(expectedTypes);
        if (!found) {
            System.err.println("Was unable to locate all types!\nExpected:" + expectedTypes + "\nFound:" + allTypes);
            System.exit(1);
        }
    }

    private class MyInnerClass {
    }
}

