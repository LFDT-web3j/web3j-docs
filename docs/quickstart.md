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

## Create a project with the CLI

Install the CLI using the [installation instructions](command_line_tools.md#installation), then create the Hello World sample:

```bash
web3j new helloworld
```

Follow the prompts and open the generated project. The CLI is released separately from core, so check its generated build before running it: use Java 21, a compatible Gradle version, and the Web3j {{ web3j.version }} dependency shown above. Follow the [upgrade guide](getting_started/web3j_6.md) if the template targets an older release.

From that project directory, configure a local development network or Sepolia endpoint and a wallet funded with test ETH, as described under [running your application](command_line_tools.md#running-your-application), then run:

```bash
web3j run
```

The Hello World application deploys a contract and reads its greeting. Deployment sends a transaction and consumes gas; the balance example above is the option that needs no signing account.

## Generate contract wrappers in an existing project

You can also add generation to an existing Java project. The plugin versions are independent of core.

### Gradle

Add the plugin to the `plugins` block in `build.gradle`, alongside the core dependency and Java toolchain configuration above:

```groovy
plugins {
    id 'java'
    id 'org.web3j' version '{{ gradle_plugin.version }}'
}

web3j {
    generatedPackageName = 'org.example.contracts'
}
```

Put Solidity files in `src/main/solidity` and run:

```bash
./gradlew generateContractWrappers
```

Generated Java wrappers are placed in `build/generated/source/web3j/main/java`. See the [Gradle plugin guide](plugins/web3j_gradle_plugin.md) for compiler and output configuration.

### Maven

Add this plugin to the `build/plugins` section of `pom.xml` (keep the core dependency and Java 21 compiler configuration):

```xml
<plugin>
    <groupId>org.web3j</groupId>
    <artifactId>web3j-maven-plugin</artifactId>
    <version>{{ maven_plugin.version }}</version>
    <configuration>
        <packageName>org.example.contracts</packageName>
        <soliditySourceFiles>
            <directory>src/main/solidity</directory>
            <includes>
                <include>**/*.sol</include>
            </includes>
        </soliditySourceFiles>
    </configuration>
</plugin>
```

This explicitly selects the same Solidity directory as the Gradle example. Run:

```bash
mvn web3j:generate-sources
```

The default Java output directory is `src/main/java`. See the [Maven plugin guide](plugins/web3j_maven_plugin.md) for other layouts. With either build tool, inspect the resolved dependencies to ensure the application uses Web3j {{ web3j.version }}.

## Deploy and load a contract

The CLI's `HelloWorld` contract has a constructor taking an initial greeting and a `greeting()` view method. After generating its Java wrapper, import `HelloWorld` from the configured package. The following code belongs inside a method that declares `throws Exception` and also uses these Web3j types:

```java
import java.math.BigInteger;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticEIP1559GasProvider;
```

Load a test wallet, connect to your selected network and deploy:

```java
var credentials = WalletUtils.loadCredentials(
        System.getenv("WEB3J_WALLET_PASSWORD"), System.getenv("WEB3J_WALLET_PATH"));
Web3j web3j = Web3j.build(new HttpService(System.getenv("WEB3J_RPC_URL")));
try {
    var chain = web3j.ethChainId().send();
    if (chain.hasError()) {
        throw new IllegalStateException(chain.getError().getMessage());
    }
    // Example fee caps in wei and gas limit; adjust for the selected test network.
    var gasProvider = new StaticEIP1559GasProvider(
            chain.getChainId().longValueExact(),
            BigInteger.valueOf(30_000_000_000L),
            BigInteger.valueOf(2_000_000_000L),
            BigInteger.valueOf(1_000_000));
    HelloWorld helloWorld = HelloWorld.deploy(
            web3j, credentials, gasProvider, "Hello Blockchain World").send();
    System.out.println(helloWorld.getContractAddress());
    System.out.println(helloWorld.greeting().send());
} finally {
    web3j.shutdown();
}
```

To reuse an existing deployment on the same network, replace the `deploy(...)` call inside the `try` block with:

```java
HelloWorld helloWorld = HelloWorld.load(
        System.getenv("HELLO_WORLD_ADDRESS"), web3j, credentials, gasProvider);
if (!helloWorld.isValid()) {
    throw new IllegalStateException("Contract does not match this generated wrapper");
}
```

`load(...)` creates a local wrapper without deploying another contract. The `greeting()` view call reads contract state; state-changing methods send transactions. See [contract generation and deployment](getting_started/deploy_interact_smart_contracts.md) for more detail.
