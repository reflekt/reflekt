#!/usr/bin/env bash
./gradlew build
cd build/libs
java -jar example-reflekt-DEV.jar
