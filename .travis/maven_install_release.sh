#!/usr/bin/env bash

set -e

echo "BUILDING RELEASE TAG $1"

mvn -B release:update-versions -DdevelopmentVersion=$1-SNAPSHOT
mvn install
mvn -B versions:update-parent -DallowSnapshots=true -DparentVersion=$1-SNAPSHOT -f example/pom.xml
rm example/pom.xml.versionsBackup
git add pom.xml
git add **/pom.xml
git commit -m "Setting release version"
mvn install -f example/pom.xml
pushd example/benchmark
./test.sh
popd
pushd example/java/target
java -cp reflekt-example-java-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.example.IntegrationTest
popd
