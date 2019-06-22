# RefleKt

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.reflekt/reflekt-core.svg)](https://search.maven.org/artifact/io.github.reflekt/reflekt-core)
[![Project Status: Active – The project has reached a stable, usable state and is being actively developed.](https://www.repostatus.org/badges/latest/active.svg)](https://www.repostatus.org/#active)
![Travis (.org)](https://img.shields.io/travis/reflekt/reflekt.svg?label=Linux%20build)
![AppVeyor](https://img.shields.io/appveyor/ci/jensim/reflekt-3gs91.svg?label=windows%20build)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=io.github.reflekt%3Areflekt-parent&metric=coverage)](https://sonarcloud.io/dashboard?id=io.github.reflekt%3Areflekt-parent)

I bloody love [org.reflections](https://github.com/ronmamo/reflections). 
I use it in testing all the time, I've even used it in production once or twice. 
(But) there is one or two things thing that bothers me though.
- Its a bit complex using the constructor with its vararg untyped optional extra scanners
- Its a bit slow starting, since it builds all its internal indexes at start, scanning things you might not use, if you only need class names, and not actual classes loaded.
- It gives you transitive dependencies to google-guava and jboss-javassist, that you might not want to have.
- It doesnt seem to work well with jigsaw (java modules)

Im doing this for fun, and maybe there will be someone else who has similar needs as mine.

## Mission:
- Thread and concurrency safe
- Lazy init of all class discovery and analysis
- Zero dependencies
- Simple construction
- Easy to test/mock
- Works with jigsaw out of the box
- Works well with Java and Kotlin
- Nice syntax

## Dependency management:
#### Maven dependency:
````xml
<!-- Using maven -->
<dependency>
    <groupId>io.github.reflekt</groupId>
    <artifactId>reflekt-core</artifactId>
    <version>${reflekt.version}</version>
    <scope>test</scope> <!-- Think twice before using reflection libs in prod -->
</dependency>
````

#### Gradle:
```build.gradle
dependencies {
    // Think twice before using reflection libs in prod
    testImplementation("io.github.reflekt:reflekt-core:${reflekt.version}")
}
```

## Usage:
```java
import static io.github.reflekt.ReflektBuilder.reflekt;
import static io.github.reflekt.ReflektConf;
RefleKt r = reflekt(); //For all class files found in jar or classpath
r = reflekt("com.example"); //Ristrict package prefix to "com.example"
ReflektConf conf = ReflektConf.builder()
    .setIncludeNestedJars(true) // Look for class files in nested jars
    .setPackageFilter("com.example")
    .setExtraClassFileLocators(List.of()) // Add a list of extra class file locators, in case you've hidden your classes where I cannot find em
    .build();
r = reflekt(conf); 
```

## Skipped features of org.reflections
In my very opinionated opinion, these methods do not belong/are not needed in a slim reflection library, and are therefore left out.
- getResources
- getMethodParamNames
- getConstructorParamNames
- getFieldUsage
- getMethodUsage
- getConstructorUsage
