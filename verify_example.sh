#!/usr/bin/env bash

echo "Building RefleKt"
./gradlew assemble publishToMavenLocal --stacktrace
cd example
./test.sh
