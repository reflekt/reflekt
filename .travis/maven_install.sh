#!/usr/bin/env bash

set -v

if [[ -z "${1}" ]]; then
    .travis/maven_install_plain.sh
else
    .travis/maven_install_release.sh $1
fi
