#!/usr/bin/env bash

set -e
set -v

if [[ -z "${TRAVIS_TAG}" ]]; then
    echo "BUILDING"
    ./mvnw install
    ./mvnw install -f example/pom.xml
else
    echo "BUILDING RELEASE TAG ${TRAVIS_TAG}"
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
