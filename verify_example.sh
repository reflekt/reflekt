#!/usr/bin/env bash

mvn clean install -DskipTests
cd example/benchmark
./test.sh
