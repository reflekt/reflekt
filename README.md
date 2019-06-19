# RefleKt
# Work in progress

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Project Status: WIP – Initial development is in progress, but there has not yet been a stable, usable release suitable for the public.](https://img.shields.io/badge/Repo%20status-Work%20in%20progress-yellow.svg)](https://www.repostatus.org/#wip)
[![Build Status](https://travis-ci.org/reflekt/reflekt.svg?branch=master)](https://travis-ci.org/reflekt/reflekt)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.reflekt%3Areflekt-parent&metric=coverage)](https://sonarcloud.io/dashboard?id=org.reflekt%3Areflekt-parent)

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

```java
import static org.reflekt.ReflektBuilder.reflekt;
import static org.reflekt.ReflektConf;
RefleKt r = reflekt(); //For all class files found in jar or classpath
r = reflekt("com.example"); //Ristrict package prefix to "com.example"
ReflektConf conf = ReflektConf.builder()
    .setIncludeNestedJars(true) // Look for class files in nested jars
    .setPackageFilter("com.example")
    .setExtraClassFileLocators(List.of()) // Add a list of extra class file locators, in case you've hidden your classes where I cannot find em
    .build();
r = reflekt(conf); 
```
