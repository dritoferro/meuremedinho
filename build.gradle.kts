import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.71"
    `maven-publish`
    id("io.spring.dependency-management") version ("1.0.9.RELEASE")
    id("com.github.johnrengelman.shadow") version ("6.0.0")
}

group = "br.com.tagliaferrodev"
version = "dev"

description = "hello"


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<ShadowJar> {
    transform(Log4j2PluginsCacheFileTransformer::class.java)
}

repositories {
    mavenCentral()
}

// If requiring AWS JDK, uncomment the dependencyManagement to use the bill of materials
//   https://aws.amazon.com/blogs/developer/managing-dependencies-with-aws-sdk-for-java-bill-of-materials-module-bom/
dependencyManagement {
    imports {
        mavenBom("software.amazon.awssdk:bom:2.15.33")
    }
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib:1.3.71")
    api("com.amazonaws:aws-lambda-java-core:1.2.1")
    api("com.amazonaws:aws-lambda-java-log4j2:1.2.0")
    api("org.slf4j:slf4j-simple:1.7.30")
    api("com.fasterxml.jackson.core:jackson-core:2.11.0")
    api("com.fasterxml.jackson.core:jackson-databind:2.11.0")
    api("com.fasterxml.jackson.core:jackson-annotations:2.11.0")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")

    api("com.amazon.alexa:ask-sdk:2.20.2")

    api("software.amazon.awssdk:dynamodb")

    testImplementation(kotlin("test-junit"))
}

tasks.build {
    finalizedBy(getTasksByName("shadowJar", false))
}

task<Exec>("deploy") {
    dependsOn("shadowJar")
    commandLine("serverless", "deploy")
}
