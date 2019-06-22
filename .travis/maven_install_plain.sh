#!/usr/bin/env bash

set -e

echo "BUILDING PLAIN"

set -v

mvn install
mvn install -f example/pom.xml
