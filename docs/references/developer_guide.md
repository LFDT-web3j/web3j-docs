# Developer guide

## Dependencies

Use published releases from [Maven Central](https://central.sonatype.com/artifact/org.web3j/core/{{ web3j.version }}). Follow [manual configuration](../getting_started/manual_configuration.md) for Web3j {{ web3j.version }} and Java 21.

Snapshot builds are development artifacts and may change. For current snapshot repository settings, follow the [core project’s publishing configuration](https://github.com/LFDT-web3j/web3j); do not use the retired OSSRH URLs from older guides.

## Build Web3j

Clone [the core repository](https://github.com/LFDT-web3j/web3j) and follow its contributor instructions. With the required JDK installed, use the wrapper:

```bash
./gradlew check
```

The integration suite is enabled separately:

```bash
./gradlew -Pintegration-tests=true :integration-tests:test
```

See the repository’s current CI setup for Docker and client requirements.

## Build this documentation

The guides use MkDocs and live in the [web3j-docs repository](https://github.com/LFDT-web3j/web3j-docs). Its README lists the pinned Python dependencies, strict build, Java example tests and link checks. Documentation PRs run those checks before publication.

The API reference comes from the [published Web3j artifact](../javadoc-api/index.md); it is separate from the MkDocs guide source.
