#!/usr/bin/env bash

./gradlew assemble publishToMavenLocal --stacktrace
cd example
./test.sh
