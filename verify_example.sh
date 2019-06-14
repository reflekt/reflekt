#!/usr/bin/env bash

mvn clean install
cd example/benchmark
./test.sh
