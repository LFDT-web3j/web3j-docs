# Manual configuration

Web3j **{{ web3j.version }}** requires **Java 21 or newer**. Use Maven Central to resolve the library. Android has a [separate release line](setup_android.md).

## Maven

```xml
<properties>
  <maven.compiler.release>21</maven.compiler.release>
</properties>
<dependencies>
  <dependency>
    <groupId>org.web3j</groupId>
    <artifactId>core</artifactId>
    <version>{{ web3j.version }}</version>
  </dependency>
</dependencies>
```

`maven.compiler.release` tells the compiler to target Java 21 language features, APIs and bytecode, matching Web3j 6’s Java baseline. It does not install a JDK: run Maven with Java 21 or newer. If your project already configures release 21 (or newer) through a parent POM or toolchain, keep that configuration instead of duplicating it.

Use a current Maven Compiler Plugin with Java 21 support. The [quickstart](../quickstart.md) provides a pinned build configuration.

## Gradle

Use a Gradle version that supports your JDK. For example, Gradle 8.5 or newer can run on Java 21.

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation 'org.web3j:core:{{ web3j.version }}'
}
```

Use `implementation`, rather than the removed `compile` configuration.

## Contract generation

The [Gradle plugin](../plugins/web3j_gradle_plugin.md) and [Maven plugin](../plugins/web3j_maven_plugin.md) generate Java contract wrappers. Their versions are independent of the core library: the published plugin versions used by this guide are {{ gradle_plugin.version }} and {{ maven_plugin.version }}, respectively. Check the generated project’s resolved core dependency when upgrading to Web3j 6.

Continue with the [quickstart](../quickstart.md) to make a read-only RPC request.
