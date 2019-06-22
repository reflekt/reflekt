#!/usr/bin/env bash

set -e

if [[ -z "${TRAVIS_TAG}" ]]; then
    ./mvnw install
    ./mvnw install -f example/pom.xml
else
    ./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT
    ./mvnw install
    ./mvnw -B versions:update-parent -DallowSnapshots=true -DparentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
    ./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
    rm example/pom.xml.versionsBackup
    git add **/pom.xml
    git commit -m "Setting release version"
    ./mvnw install -f example/pom.xml
fi
pushd example/benchmark
. ./test.sh
popd
./mvnw sonar:sonar
