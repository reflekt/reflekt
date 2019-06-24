package com.example;

import io.github.reflekt.Reflekt;
import io.github.reflekt.ReflektBuilder;
import io.github.reflekt.ReflektConf;

import java.util.Set;
import java.util.stream.Collectors;

public class IntegrationTest {

    public static void main(String[] args) {
        // given
        ReflektConf conf = ReflektConf.builder()
                .setIncludeNestedJars(false)
                .setPackageFilter("com.example")
                .build();
        Reflekt reflekt = ReflektBuilder.reflekt(conf);
        Set<String> expectedTypes = Set.of(MyInnerClass.class, MyOuterClass.class).stream()
                .map(Class::getCanonicalName)
                .collect(Collectors.toSet());

        // when
        Set<String> allTypes = reflekt.getAllTypes();

        // then
        var found = allTypes.containsAll(expectedTypes);
        if (!found) {
            System.err.println("Was unable to locate all types!\nExpected:" + expectedTypes + "\nFound:" + allTypes);
            System.exit(1);
        } else {
            System.out.println("OK!");
        }
    }

    private class MyInnerClass {
    }
}

