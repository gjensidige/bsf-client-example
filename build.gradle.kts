import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "2.0.0"
    id("org.openapi.generator") version "7.5.0"
    application
}

group = "no.gjensidige.bsf.example"
version = "1.0-SNAPSHOT"
application {
    mainClass = "$group.MainKt"
}

repositories {
    mavenCentral()
}

val ktor_version: String by project
val jackson_version: String by project
val kotlinx_serialization: String by project
val logback_version: String by project
val slf4j_version: String by project

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-java:$ktor_version")
    implementation("io.ktor:ktor-client-auth:$ktor_version")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

val apiRootName = "no.gjensidige.bsf.api.client"
val generatedSourcesPath = "$buildDir/generated"

openApiGenerate {
    generatorName = "kotlin"
    inputSpec = "$rootDir/src/main/resources/openapi.yaml".replace("\\","/")
    outputDir = generatedSourcesPath
    apiPackage = "$apiRootName.api"
    modelPackage = "$apiRootName.model"
    invokerPackage = "$apiRootName.invoker"
    modelNameSuffix = "Dto"
    configOptions = mapOf(
        "idea" to "true",
        "library" to "jvm-ktor",
        "enumPropertyNaming" to "UPPERCASE",
        "serializationLibrary" to "kotlinx_serialization"
    )
}

kotlin.sourceSets["main"].kotlin.srcDirs("$generatedSourcesPath/src/main/kotlin")

tasks.withType<KotlinCompile>().configureEach {
    dependsOn("openApiGenerate")
}
