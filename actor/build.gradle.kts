plugins {
    kotlin("jvm") version "1.4.0"
}

group = "com.kochetkov"
version = "0.1.0"

kotlin {
    this.sourceSets {
        main {
            kotlin.srcDirs("src/main/java")
            kotlin.srcDirs("src/main/kotlin")
            resources.srcDirs("src/main/resources")
        }

        test {
            kotlin.srcDirs("src/test/java")
            kotlin.srcDirs("src/test/kotlin")
            resources.srcDirs("src/test/resources")
        }
    }

    version = "1.4.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("stdlib"))
    implementation("com.typesafe.akka:akka-actor_2.11:2.4.17")
    implementation("org.mock-server:mockserver-netty:5.10")
    testImplementation("junit:junit:4.13")
}

tasks.test {
    useJUnit()

    maxParallelForks = 4
    maxHeapSize = "2G"
    reports.html.isEnabled = false
}