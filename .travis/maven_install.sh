#!/usr/bin/env bash

set -e
set -v

if [[ -z "${TRAVIS_TAG}" ]]; then
    echo "BUILDING"
    mvn install
    mvn install -f example/pom.xml
else
    echo "BUILDING RELEASE TAG ${TRAVIS_TAG}"
    mvn -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT
    mvn install
    mvn -B versions:update-parent -DallowSnapshots=true -DparentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
    mvn -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
    rm example/pom.xml.versionsBackup
    git add **/pom.xml
    git commit -m "Setting release version"
    mvn install -f example/pom.xml
fi
pushd example/benchmark
. ./test.sh
popd
mvn sonar:sonar
