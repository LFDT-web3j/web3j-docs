# Manual configuration

Web3j **{{ web3j.version }}** requires **Java 21 or newer**. Use Maven Central to resolve the library. Android has a [separate release line](setup_android.md).

## Maven

Add the dependency to your Java 21 project:

```xml
<dependencies>
  <dependency>
    <groupId>org.web3j</groupId>
    <artifactId>core</artifactId>
    <version>{{ web3j.version }}</version>
  </dependency>
</dependencies>
```

Keep your existing Java build configuration. For a complete runnable example, follow the [quickstart](../quickstart.md).

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
