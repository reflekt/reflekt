#!/usr/bin/env bash

if [[ -z "${TRAVIS_TAG}" ]]; then
    ./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT
    ./mvnw -B release:update-versions -DdevelopmentVersion=${TRAVIS_TAG}-SNAPSHOT -f example/pom.xml
#else
fi
