# Transaction mechanisms

Web3j supports local signing and requests to a node that manages its own signing account. For modern applications, choose explicitly where signing keys are held and which component is allowed to sign.

## Offline transaction signing

Create a `RawTransaction` with the intended chain ID, nonce, gas limit and fees. Sign it with `TransactionEncoder.signMessage(...)` and submit the signed bytes through `web3j.ethSendRawTransaction(...)`. The execution node receives a signed transaction; it does not need your private key or wallet password.

This EIP-1559 example separates signing from submission, so signing can run offline:

```java
--8<-- "examples/src/main/java/org/web3j/docs/RawTransactionExample.java"
```

Load `credentials` using `WalletUtils.loadCredentials(password, walletFile)`. Obtain the intended network's chain ID and the sender's pending nonce before signing; if multiple transactions are in flight, coordinate nonce allocation in your application. Pass a gas limit appropriate to the recipient and fee caps appropriate to the network. All values and fees are in wei. With those values prepared:

```java
String signed = RawTransactionExample.signEther(
        credentials, chainId, nonce, gasLimit, recipient, value,
        maxPriorityFeePerGas, maxFeePerGas);
String transactionHash = RawTransactionExample.submit(web3j, signed);
```

Use the [EIP-1559 transaction guide](EIP_transaction_types/eip1559_transaction.md) for fee fields, [transaction nonce](transaction_nonce.md) for ordering and [wallet files](wallet_files.md) for encrypted credentials. Check the RPC response for errors and wait for a transaction receipt before treating submission as execution. A successful submission alone does not mean the transaction succeeded.

For keys managed outside the application, see [HSM transaction signing](../advanced/HSM_transaction_signing.md). Test signing and submission on an isolated development chain or Sepolia before using funded accounts.

## Transaction signing via an Ethereum client

`web3j.ethSendTransaction(transaction)` asks the node to sign and submit a transaction from a node-managed account. It works only when the connected client has an appropriate signer configured and exposes this method. A hosted RPC endpoint normally does not hold your keys.

For a node with a signer configured for `sender`, this example uses a legacy gas-price transaction. `nonce`, `gasPrice`, `gasLimit` and `value` are `BigInteger` values; `sender` and `recipient` are address strings:

```java
import org.web3j.protocol.core.methods.request.Transaction;

// Inside a method that handles IOException:
Transaction transaction = Transaction.createEtherTransaction(
        sender, nonce, gasPrice, gasLimit, recipient, value);
var response = web3j.ethSendTransaction(transaction).send();
if (response.hasError()) {
    throw new IllegalStateException(response.getError().getMessage());
}
String transactionHash = response.getTransactionHash();
```

For contract creation, use `Transaction.createContractTransaction(sender, nonce, gasPrice, gasLimit, value, initCode)` instead. For local signing, the corresponding legacy factory is `RawTransaction.createContractTransaction(nonce, gasPrice, gasLimit, value, initCode)`; sign a legacy transaction with the chain-ID overload `TransactionEncoder.signMessage(transaction, chainId, credentials)`. See [construction and deployment](../smart_contracts/construction_and_deployment.md) for constructor encoding and generated wrappers.

Older recipes used `personal_newAccount` and `personal_unlockAccount` with Geth or OpenEthereum. These are legacy APIs: Geth's personal namespace is deprecated and OpenEthereum is archived. Do not expose an unlocked account over a public RPC endpoint. See [management APIs](../advanced/management_apis.md) for compatibility details.
