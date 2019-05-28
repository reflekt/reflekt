plugins {
    kotlin("jvm") version "1.3.31"
    java
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "com.example"
version = findProperty("releaseVersion") ?: "DEV"

val mainClass = "com.example.annotations.Main"

repositories {
    mavenCentral()
    mavenLocal()
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("se.jensim.reflekt:reflekt:DEV")
    implementation("org.reflections:reflections:0.9.11")

    testImplementation(kotlin("test-junit"))
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("org.hamcrest:hamcrest-library:2.1")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.shadowJar {
    minimize()
    manifest {
        attributes["Main-Class"] = mainClass
    }
}
