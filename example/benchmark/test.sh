#!/usr/bin/env bash

echo "Building Example project with benchmarks"

pushd target

JAR="$(ls  *jar-with-dependencies.jar | sort -V | tail -n1)"
MAIN="com.example.Main"

java -cp ${JAR} ${MAIN} 3 null        RefleKt         org.reflections
java -cp ${JAR} ${MAIN} 3 null        org.reflections RefleKt
java -cp ${JAR} ${MAIN} 3 com.example RefleKt         org.reflections
java -cp ${JAR} ${MAIN} 3 com.example org.reflections RefleKt

popd
