# Upgrading to Web3j 6

This guide targets **{{ web3j.version }}**. The [release notes](https://github.com/LFDT-web3j/web3j/releases/tag/v6.0.0) describe the changes from 5.0.3.

## Upgrade checklist

- Use Java 21 or newer and update the core dependency to `org.web3j:core:{{ web3j.version }}`.
- Keep Web3j core modules on the same release. Inspect your Maven or Gradle dependency tree for older transitive Web3j modules.
- Treat build plugins, Android and OpenAPI as independent release lines; their version numbers do not follow core automatically.
- Regenerate contract wrappers where appropriate, compile your application, and run integration tests against your target execution client before deployment.
- Read the [current API reference](../javadoc-api/index.md) rather than the historical bundled Javadoc.

## APIs added in 6.0.0

### EIP-7702 authorizations

`Sign.signAuthorization(...)` produces an authorization tuple, and `Sign.recoverAuthorizationSigner(...)` recovers its signer. The outer transaction signature and each authorization signature are distinct. Use the authority account’s nonce and the intended chain ID; never treat placeholder signature integers as a valid authorization. See [EIP-7702 transactions](../transactions/EIP_transaction_types/eip7702_transaction.md).

### EIP-7594 blob transaction wrappers

`RawTransaction.createEip7594Transaction(...)` supports the EIP-7594 wrapper and cell-proof generation. Consult the [crypto API](https://javadoc.io/doc/org.web3j/crypto/{{ web3j.version }}/index.html) and the release’s [transaction encoder tests](https://github.com/LFDT-web3j/web3j/blob/v6.0.0/crypto/src/test/java/org/web3j/crypto/TransactionEncoderTest.java) for the payload and proof requirements. Check that the receiving node supports the relevant network upgrade and transaction format.

### Blob base fee

`web3j.ethBlobBaseFee().send()` calls `eth_blobBaseFee`, returning the blob base fee used by the updated blob-fee rules. Check `hasError()` before consuming the response: a node that does not implement this method may return an RPC error.

## Compatibility boundaries

The [Android guide](setup_android.md) uses `{{ android.version }}`, not the Java 21 core artifact. The [OpenAPI client](../web3j_openapi.md) also has a separate release and requires its own compatibility validation. Updating the core dependency does not establish compatibility for either environment.
