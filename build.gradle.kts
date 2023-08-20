import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("io.qameta.allure") version "2.10.0"
    id("org.springframework.boot") version "2.7.3"
    id("org.jetbrains.kotlin.plugin.spring") version "1.5.31"
}

group = "ru.gogolev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion: String by project
val restAssuredVersion: String by project
val jacksonVersion: String by project
val allureVersion: String by project
val springVersion: String by project
val kotestVersion: String by project

dependencies {
    //////////////////////////////////////////////////
    //                  JUnit5                      //
    //////////////////////////////////////////////////
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    //////////////////////////////////////////////////
    //                  RestAssured                 //
    //////////////////////////////////////////////////
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")

    //////////////////////////////////////////////////
    //                  Jackson                     //
    //////////////////////////////////////////////////
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    //////////////////////////////////////////////////
    //                  Spring                      //
    //////////////////////////////////////////////////
    implementation("org.springframework.boot:spring-boot-starter:$springVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")

    //////////////////////////////////////////////////
    //                  Kotest                      //
    //////////////////////////////////////////////////
    implementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    implementation("io.kotest:kotest-property:$kotestVersion")

    //////////////////////////////////////////////////
    //                  Allure                      //
    //////////////////////////////////////////////////
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")

    //////////////////////////////////////////////////
    //                  External Libs               //
    //////////////////////////////////////////////////
    runtimeOnly("org.aspectj:aspectjweaver:1.9.19")
}

allure {
    version.set(allureVersion)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}