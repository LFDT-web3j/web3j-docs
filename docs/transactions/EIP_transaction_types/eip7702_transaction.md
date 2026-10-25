# EIP-7702 transactions

[EIP-7702](https://eips.ethereum.org/EIPS/eip-7702) introduces transaction type `0x04` for account-code delegation. Its authorization list contains separately signed tuples; signing the outer transaction does not sign those tuples.

## Sign an authorization with Web3j 6

```java
--8<-- "examples/src/main/java/org/web3j/docs/AuthorizationExample.java"
```

Pass the authority account’s credentials, the intended chain ID, the delegate contract address and the authority account’s nonce. `Sign.recoverAuthorizationSigner(tuple)` recovers the authority address. The documentation tests verify signing and recovery without broadcasting a transaction.

!!! warning "Authorizations grant execution authority"
    Verify the delegate code and nonce before signing. An authorization for chain ID zero is not restricted to one chain. Keep keys and signed authorizations out of logs and source control.

## Build and send the transaction

Supply the signed authorization tuples and access list to the EIP-7702 overload of `RawTransaction.createTransaction(...)`. The transaction sender has its own nonce, gas limit and EIP-1559 fee fields. When the authority is also the transaction sender, account for the sender nonce increment before authorization processing: for a single authorization, use the transaction nonce plus one as the authorization nonce. Sign the outer transaction with `TransactionEncoder.signMessage(...)`, then submit it using `web3j.ethSendRawTransaction(...)`. Check the RPC response and receipt for failure.

See the [crypto API](https://javadoc.io/doc/org.web3j/crypto/{{ web3j.version }}/index.html) for exact overloads. The connected node and network must support EIP-7702. Transactions returned by JSON-RPC expose authorization data through `Transaction.getAuthorizationList()`.
