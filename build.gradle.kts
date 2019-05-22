plugins {
    kotlin("jvm") version "1.3.31"
    jacoco
    id("org.sonarqube") version "2.7.1"
}

group = "se.jensim.reflekt"
version = findProperty("releaseVersion") ?: "DEV"

repositories {
    mavenCentral()
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.javassist:javassist:3.25.0-GA")

    testImplementation(kotlin("test-junit"))
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.destination = file("$buildDir/jacocoHtml")
    }
}