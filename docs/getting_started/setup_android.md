Setting up Project for Android
--------------------------------------------------------

In this article, we’ll walk through the steps to set up Web3j for Android development using the separately published Android release.

!!! note "Android is a separate release line"
    The dependency below is the Android artifact, not the Java 21 core {{ web3j.version }} release. Select a JDK supported by your Android Gradle Plugin and validate the dependency on your target devices.

## Step 1: Add Web3j Dependency

### Using Maven:

Add the following dependency to your pom.xml file:

```xml
<dependency>
 <groupId>org.web3j</groupId>
 <artifactId>core</artifactId>
 <version>{{ android.version }}</version>
</dependency>
```

### Using Gradle (Kotlin):

Add the Web3j dependency to your build.gradle.kts file :

```kotlin
dependencies {
   implementation("org.web3j:core:{{ android.version }}")
}
```

## Step 2: Update Packaging Options

To avoid conflicts with certain files included in the Web3j library, you need to exclude specific resources. Add the following code snippet to your module’s build.gradle.kts file within the android block:

```kotlin
android {
   packagingOptions {
       resources {
           excludes += "/META-INF/DISCLAIMER"
       }
   }
}
```



