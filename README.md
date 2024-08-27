# Boligselgersikring API Client Example

Example configuration and code to consume Gjensidiges boligselgerforsikring API.

Main.kt contains code to execute the send egenerklaering step against Gjensidiges test environment.
The tests provide a more complete example of api usage/flow, but the underlying api is mocked to make the tests pass.

## How to run

Make sure the client id and client secret in TokenStorage.kt is changed to real values before running the example.

### IDE

Open Main.kt in your favorite IDE and run the file. Depending on your editor you might have to install a kotlin/gradle
plugin in order to run the file. For Intellij this comes out-of-the-box.

### Terminal
#### macos / linux

In your favorite shell/terminal, run:

```shell
./gradlew run
```

#### Windows

From the Windows Command Prompt, run:

```shell
gradlew.bat run
```

## Technology overview

Note that these can be swapped out with ones own desired suite of technologies, the exception being OpenAPI, the 
essential part of this example documentation.

### OpenAPI

<https://learn.openapis.org/>

OpenAPI is a strict specification language for HTTP APIs. If an openapi document is provided both client and server
code can be generated from it.

### OpenAPI Generator

<https://openapi-generator.tech/>

An unofficial, but widely adopted tool used to generate server and client code from openapi documents. Target languages
are many, including kotlin, javascript, C#, and many more.

In this project it is part of the build, specifically in the 'openApiGenerate' gradle task. This build stage will
generate the module 'no.gjensidige.bsf.api.client' which you can see is imported in Main.kt. The module contains the
api client object as well as DTO classes.

For a full list of configuration options available for the kotlin generator used, see
https://openapi-generator.tech/docs/generators/kotlin/

### Gradle

<https://docs.gradle.org/current/userguide/userguide.html>

Gradle is a build automation tool. It provides a means to generate, compile and run the example code.

### Kotlin

<https://kotlinlang.org/docs/home.html>

Kotlin is a multiparadigmal language that can used to build appliations on the JVM.

### Ktor

<https://ktor.io/docs/welcome.html>

Ktor is a framework for building both server-side and client-side kotlin applications. Here it is used as the backbone
for the client implementation.

### Kotlinx

<https://kotlinlang.org/docs/serialization.html#example-json-serialization>

Kotlinx Serialization is an official kotlin serialization library, and is used as the client serializer and deserializer.
