#!/usr/bin/env bash

set -e
set -v

openssl aes-256-cbc -K $1 -iv $2 -in .travis/gpg/jens.brimfors-secret-gpg.key.enc -out jens.brimfors-secret-gpg.key -d
gpg --import .travis/gpg/jens.brimfors-public-gpg.key
gpg --import jens.brimfors-secret-gpg.key
gpg --import-ownertrust .travis/gpg/jens.brimfors-ownertrust-gpg.txt
cp .travis/settings.xml $HOME/.m2/settings.xml
mvn -B release:prepare
mvn release:perform

set +v
