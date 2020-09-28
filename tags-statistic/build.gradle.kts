plugins {
    kotlin("jvm") version "1.4.0"
}

group = "ru.ifmo.kochetkov"
version = "0.1.0"

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
    reports.html.isEnabled = false
}