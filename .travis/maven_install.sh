#!/usr/bin/env bash

set -e

if [[ -z "${TRAVIS_TAG}" ]]; then
    . .travis/maven_install_plain.sh
else
    . .travis/maven_install_release.sh
fi
pushd example/benchmark
. ./test.sh
popd
mvn sonar:sonar
