plugins {
    kotlin("jvm") version "1.3.31"
    application
}

group = "com.example"
version = findProperty("releaseVersion") ?: "DEV"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.jensim:reflekt:0.0.3")

    testImplementation(kotlin("test-junit"))
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("org.hamcrest:hamcrest-library:2.1")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.example.annotations.Main"
}
