#!/usr/bin/env bash

set -e

echo "BUILDING PLAIN"

mvn install
mvn install -f example/pom.xml
pushd example/benchmark
. ./test.sh
popd
mvn sonar:sonar
