# Transaction mechanisms

Web3j supports local signing and requests to a node that manages its own signing account. For modern applications, choose explicitly where signing keys are held and which component is allowed to sign.

## Offline transaction signing

Create a `RawTransaction` with the intended chain ID, nonce, gas limit and fees. Sign it with `TransactionEncoder.signMessage(...)` and submit the signed bytes through `web3j.ethSendRawTransaction(...)`. The execution node receives a signed transaction; it does not need your private key or wallet password.

Use the [EIP-1559 transaction guide](EIP_transaction_types/eip1559_transaction.md) for fee fields, [transaction nonce](transaction_nonce.md) for ordering and [wallet files](wallet_files.md) for encrypted credentials. Check the RPC response for errors and wait for a transaction receipt before treating submission as execution. A successful submission alone does not mean the transaction succeeded.

For keys managed outside the application, see [HSM transaction signing](../advanced/HSM_transaction_signing.md). Test signing and submission on an isolated development chain or Sepolia before using funded accounts.

## Transaction signing via an Ethereum client

`web3j.ethSendTransaction(transaction)` asks the node to sign and submit a transaction from a node-managed account. It works only when the connected client has an appropriate signer configured and exposes this method. A hosted RPC endpoint normally does not hold your keys.

Older recipes used `personal_newAccount` and `personal_unlockAccount` with Geth or OpenEthereum. These are legacy APIs: Geth's personal namespace is deprecated and OpenEthereum is archived. Do not expose an unlocked account over a public RPC endpoint. See [management APIs](../advanced/management_apis.md) for compatibility details.
