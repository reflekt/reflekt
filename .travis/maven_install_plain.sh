#!/usr/bin/env bash

set -e

echo "BUILDING PLAIN"

mvn install
mvn install -f example/pom.xml
pushd example/java/target
java -cp reflekt-example-java-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.example.IntegrationTest
popd
pushd example/benchmark
./test.sh
popd
mvn sonar:sonar
