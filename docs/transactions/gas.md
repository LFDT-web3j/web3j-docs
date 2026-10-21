# Gas and transaction fees

Gas measures the computational work performed by the EVM. The **gas limit** caps the amount of gas a transaction may use; it is not a fee quoted in ETH. Unused gas is not charged, while a failed execution can still consume gas. See [Ethereum’s gas guide](https://ethereum.org/en/developers/docs/gas/).

## Estimate execution gas

Use `web3j.ethEstimateGas(transaction).send()` with the intended sender, recipient, value and call data. Check the response for an RPC error before reading `getAmountUsed()`. An estimate depends on current chain state and is not a guarantee that execution will succeed later. Choose an appropriate margin and respect the target network’s current limits.

## Select transaction fees

For legacy transactions, `gasPrice` is the price per gas unit in wei. For [EIP-1559 transactions](EIP_transaction_types/eip1559_transaction.md), set `maxFeePerGas` and `maxPriorityFeePerGas`; the base fee changes with network demand. Do not confuse gas-unit estimates with gas-price or priority-fee estimates.

For example, the maximum execution-fee budget for an EIP-1559 transaction is `gasLimit * maxFeePerGas`. Blob transactions have an additional blob-gas fee budget; Web3j 6 exposes `ethBlobBaseFee()` for the current blob base fee when the node supports that RPC.

## Contract gas providers

Pass a `ContractGasProvider` when deploying or loading generated contract wrappers. `DefaultGasProvider` uses fixed values; it does not fetch live fee estimates. `StaticGasProvider` accepts a caller-supplied gas price and gas limit. For EIP-1559 contract transactions, use the appropriate EIP-1559 gas-provider API and fee fields for your transaction manager.

See the [Web3j gas-provider API](https://javadoc.io/static/org.web3j/core/{{ web3j.version }}/org/web3j/tx/gas/package-summary.html). Select values for the target network and operation instead of copying historical gas-price or block-limit constants.
