plugins {
    kotlin("jvm") version "1.3.31"
}
repositories {
	mavenCentral()
}
dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))

	testImplementation(kotlin("test-junit"))
}
