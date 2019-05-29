#!/usr/bin/env bash

echo "Building Example project with benchmarks"
./gradlew build shadowJar --stacktrace --refresh-dependencies
cd build/libs
java -jar example-reflekt-DEV-all.jar 25 null reflekt org.reflections
java -jar example-reflekt-DEV-all.jar 25 null org.reflections reflekt
java -jar example-reflekt-DEV-all.jar 25 com.example.annotations reflekt org.reflections
java -jar example-reflekt-DEV-all.jar 25 com.example.annotations org.reflections reflekt
