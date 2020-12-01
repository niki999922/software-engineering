plugins {
    kotlin("jvm") version "1.4.20"
}

group = "com.kochetkov"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("junit:junit:4.13")
}

tasks.test {
    useJUnit()

    maxParallelForks = 4
    maxHeapSize = "2G"
    reports.html.isEnabled = true
}