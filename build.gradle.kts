plugins {
    kotlin("jvm") version "1.3.31"
    jacoco
    id("org.sonarqube") version "2.7.1"
    `maven-publish`
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
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("org.hamcrest:hamcrest-library:2.1")
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.destination = file("$buildDir/jacocoHtml")
    }
}

publishing {
    publications {
        create<MavenPublication>("reflekt"){
            pom {
                name.set("RefleKt")
                description.set("Like org.reflections, hoping it will be faster..")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
            }

        }
    }
}
