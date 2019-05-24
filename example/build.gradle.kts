plugins {
    kotlin("jvm") version "1.3.31"
    java
}

group = "com.example"
version = findProperty("releaseVersion") ?: "DEV"

val mainClass = "com.example.annotations.Main"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("se.jensim.reflekt:reflekt:DEV")

    testImplementation(kotlin("test-junit"))
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("org.hamcrest:hamcrest-library:2.1")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = mainClass
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}
