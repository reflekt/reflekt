package com.example;

import io.github.reflekt.Reflekt;
import io.github.reflekt.ReflektBuilder;
import io.github.reflekt.ReflektConf;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegrationTest {

    public static void main(String[] args) {
        // given
        ReflektConf conf = ReflektConf.builder()
                .setIncludeNestedJars(false)
                .setPackageFilter("com.example")
                .build();
        Reflekt reflekt = ReflektBuilder.reflekt(conf);
        Set<String> expectedTypes = Stream.of(MyInnerClass.class, MyOuterClass.class)
                .map(Class::getCanonicalName)
                .collect(Collectors.toSet());

        // when
        Set<String> allTypes = reflekt.getAllTypes();

        // then
        boolean found = allTypes.containsAll(expectedTypes);
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

