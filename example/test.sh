#!/usr/bin/env bash

./gradlew build --stacktrace
cd build/libs
java -jar example-reflekt-DEV.jar
