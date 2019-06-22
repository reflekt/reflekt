#!/usr/bin/env bash

set -e
set -v

openssl aes-256-cbc -K $encrypted_be6abfaeb7c9_key -iv $encrypted_be6abfaeb7c9_iv -in .travis/gpg/jens.brimfors-secret-gpg.key.enc -out jens.brimfors-secret-gpg.key -d
gpg --import .travis/gpg/jens.brimfors-public-gpg.key
gpg --import jens.brimfors-secret-gpg.key
gpg --import-ownertrust .travis/gpg/jens.brimfors-ownertrust-gpg.txt
cp .travis/settings.xml $HOME/.m2/settings.xml
./mvnw -B release:prepare
./mvnw release:perform
