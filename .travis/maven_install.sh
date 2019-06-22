#!/usr/bin/env bash

set -v

if [[ -z "${TRAVIS_TAG}" ]]; then
    . .travis/maven_install_plain.sh
else
    . .travis/maven_install_release.sh
fi

set +v
