plugins {
    kotlin("jvm") version "1.4.10"
}

group = "org.ifmo"
version = "0.1.0"

val kotlinVersion by extra("1.4.10")


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

    version = kotlinVersion
}

java {
    sourceCompatibility = JavaVersion.VERSION_12
    targetCompatibility = JavaVersion.VERSION_12
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.code.gson:gson:2.8.5")

    testImplementation("junit:junit:4.13")
    testImplementation("org.mockito:mockito-all:2.0.2-beta")
    testImplementation("com.xebialabs.restito:restito:0.8.2")
    testImplementation("org.glassfish.grizzly:grizzly-http-server:2.4.4")
}


tasks.test {
    useJUnit()

    maxParallelForks = 4
    maxHeapSize = "3G"
    reports.html.isEnabled = true
}