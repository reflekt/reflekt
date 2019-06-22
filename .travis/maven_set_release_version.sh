#!/usr/bin/env bash

set -e

if [[ -z "${TRAVIS_TAG}" ]]; then
    echo "\${TRAVIS_TAG} not set, doing nothing.."
else
    ./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT
    ./mvnw -B versions:update-parent -DallowSnapshots=true -DparentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
    ./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
    rm example/pom.xml.versionsBackup
    git add **/pom.xml
    git commit -m "Setting release version"
fi
