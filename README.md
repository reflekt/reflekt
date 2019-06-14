# RefleKt
# Work in progress

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Project Status: WIP – Initial development is in progress, but there has not yet been a stable, usable release suitable for the public.](https://img.shields.io/badge/Repo%20status-Work%20in%20progress-yellow.svg)](https://www.repostatus.org/#wip)
[![Build Status](https://travis-ci.org/jensim/refleKtions.svg?branch=master)](https://travis-ci.org/jensim/refleKtions)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=se.jensim.reflekt%3Areflekt&metric=coverage)](https://sonarcloud.io/dashboard?id=se.jensim.reflekt%3Areflekt)

I bloody love org.reflections. I use it in testing all the time, I've even used it in production once or twice. There is one or two things thing that always bothers me though.
- Its a bit complex using the constructor with its vararg untyped optional extra scanners
- Its a bit slow starting, since it builds all its internal indexes at start, scanning things you might not use
- It gives you a transitive dependency to google-guava and jboss-javassist, that you might not want to have
- It doesnt seem to work with jigsaw (java modules)

Im doing this for fun, and maybe there will be someone else who has similar needs as mine.

Therefore, my goals are
- Thread and concurrency safe
- Lazy init of all class discovery
- Zero dependencies
- Simple construction
- Easy to test/mock
- Works with jigsaw out of the box
