plugins {
    kotlin("jvm") version "1.3.72"
}

apply(plugin = "kotlin")

group = "org.kochetkov"
version = "0.1.0-SNAPSHOT"

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

    version = "1.3.72"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenLocal()
    maven(url = "https://repo1.maven.org/maven2/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.reactivex:rxjava:1.2.7")
    implementation("org.mongodb:mongodb-driver-rx:1.5.0")
    implementation("io.reactivex:rxnetty-http:0.5.3")
    implementation("io.reactivex:rxnetty-common:0.5.3")
    implementation("io.reactivex:rxnetty-tcp:0.5.2")
    implementation("io.netty:netty-all:4.1.45.Final")
    implementation("org.apache.httpcomponents:httpclient:4.5.6")
    implementation("org.apache.httpcomponents:httpcore:4.4.11")
}