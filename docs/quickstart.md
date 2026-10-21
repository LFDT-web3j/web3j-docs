# Quickstart

Start with **Java 21 or newer**, **Web3j {{ web3j.version }}**, and an Ethereum JSON-RPC endpoint. The example below reads an account balance; it needs no private key and sends no transaction.

## Add Web3j

For Maven:

```xml
<dependency>
    <groupId>org.web3j</groupId>
    <artifactId>core</artifactId>
    <version>{{ web3j.version }}</version>
</dependency>
```

For Gradle:

```groovy
repositories {
    mavenCentral()
}
dependencies {
    implementation 'org.web3j:core:{{ web3j.version }}'
}
```

See [manual configuration](getting_started/manual_configuration.md) for Java toolchain settings. Android users should follow the [Android guide](getting_started/setup_android.md).

## Read a balance

Choose a [local node](getting_started/run_node_locally.md) or a hosted [Sepolia endpoint](transactions/ethereum_testnets.md). Set `WEB3J_RPC_URL` to its HTTP URL. An address’s balance belongs to the network behind that endpoint.

```java
--8<-- "examples/src/main/java/org/web3j/docs/ReadOnlyExample.java"
```

This is the same source file compiled and tested by the documentation checks. To run it from a checkout of the [documentation repository](https://github.com/LFDT-web3j/web3j-docs):

```bash
export WEB3J_RPC_URL=http://127.0.0.1:8545
mvn -f examples/pom.xml compile exec:java \
  -Dexec.mainClass=org.web3j.docs.ReadOnlyExample \
  -Dexec.args=0x0000000000000000000000000000000000000001
```

The result is in wei. `Convert.fromWei(balance.toString(), Convert.Unit.ETHER)` converts it to ETH. Public endpoints may require credentials and rate-limit requests; do not commit provider secrets.

## Work with contracts

Follow [contract generation and deployment](getting_started/deploy_interact_smart_contracts.md) when you need typed Java wrappers. The plugin versions are independent of core:

```groovy
plugins {
    id 'org.web3j' version '{{ gradle_plugin.version }}'
}
```

For Maven, use `org.web3j:web3j-maven-plugin:{{ maven_plugin.version }}`. See the [Gradle](plugins/web3j_gradle_plugin.md) and [Maven](plugins/web3j_maven_plugin.md) guides for configuration. Put Solidity files in the configured source directory and run `./gradlew generateContractWrappers` or `mvn web3j:generate-sources`, respectively. Inspect the resolved Web3j dependencies when combining plugins with core 6.

For command-line project generation, see the [CLI guide](command_line_tools.md). For existing applications, see [upgrading to Web3j 6](getting_started/web3j_6.md).
