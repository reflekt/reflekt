#!/usr/bin/env bash

set -e

if [[ -z "${TRAVIS_TAG}" ]]; then
    echo "BUILDING"
    echo "./mvnw install"
    ./mvnw install
    echo "./mvnw install -f example/pom.xml"
    ./mvnw install -f example/pom.xml
else
    echo "BUILDING RELEASE TAG ${TRAVIS_TAG}"
    echo "./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT"
    ./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT
    echo "./mvnw install"
    ./mvnw install
    echo "./mvnw -B versions:update-parent -DallowSnapshots=true -DparentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml"
    ./mvnw -B versions:update-parent -DallowSnapshots=true -DparentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
    echo "./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml"
    ./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
    echo "rm example/pom.xml.versionsBackup"
    rm example/pom.xml.versionsBackup
    echo "git add **/pom.xml"
    git add **/pom.xml
    echo "git commit -m \"Setting release version\""
    git commit -m "Setting release version"
    echo "./mvnw install -f example/pom.xml"
    ./mvnw install -f example/pom.xml
fi
pushd example/benchmark
. ./test.sh
popd
echo "./mvnw sonar:sonar"
./mvnw sonar:sonar
