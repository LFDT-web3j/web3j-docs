Smart Contracts
===============

Web3j generates Java wrappers from a contract’s ABI and bytecode. The examples in this guide use [Solidity](https://docs.soliditylang.org/en/latest/).

In order to deploy a smart contract onto the Ethereum blockchain, it must first be compiled into a bytecode format, 
then it can be sent as part of a transaction. Web3j can do all of this for you with its [Solidity smart contract wrappers](construction_and_deployment.md#solidity-smart-contract-wrappers). To understand what is happening behind the scenes, you can refer to the details in [Creation of a smart contract](../transactions/transactions_and_smart_contracts.md#creation-of-a-smart-contract).
Given that Solidity is the language of choice for writing smart contracts, it is the language supported by Web3j, and is used for all subsequent examples.

## Examples

Please refer to [EIP-20 Ethereum token standard smart contract](../getting_started/deploy_interact_smart_contracts.md#eip-20-ethereum-token-standard-smart-contract)
