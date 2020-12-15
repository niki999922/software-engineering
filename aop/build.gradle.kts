
plugins {
    kotlin("jvm") version "1.4.20"
}

repositories {
    mavenCentral()
}

group = "com.kochetkov.aop"
version = "0.1.0"

apply(plugin = "kotlin")


java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.springframework:spring-core:5.2.0.RELEASE")
    implementation("org.springframework:spring-context:5.2.0.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.20")

    //Spring AOP + AspectJ
    implementation("org.springframework:spring-aop:5.2.0.RELEASE")
    implementation("org.aspectj:aspectjrt:1.6.11")
    implementation("org.aspectj:aspectjweaver:1.6.11")
}