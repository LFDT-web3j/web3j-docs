# Ethereum test networks

Use **Sepolia** for smart-contract and application testing. **Hoodi** is intended for validator and protocol testing. See the [Ethereum network guide](https://ethereum.org/en/developers/docs/networks/) for current networks, chain details, explorers and faucets.

Rinkeby, Ropsten, Kovan and Goerli are retired. Do not use their old faucets or RPC endpoints for new projects.

## Connect and obtain test ETH

1. Choose a Sepolia RPC endpoint from your node or provider.
2. Confirm that `eth_chainId` returns Sepolia’s chain ID, `11155111` (`0xaa36a7`).
3. Obtain test ETH using a faucet listed in the [Sepolia resources](https://ethereum.org/en/developers/docs/networks/#sepolia).
4. Configure Web3j with that endpoint. Use separate test credentials and never send real funds to a testnet.

Test ETH has no monetary value. Ethereum public testnets use proof of stake; the old CPU-mining instructions do not apply.

## Local tests

A [local development chain](../getting_started/run_node_locally.md) is useful for repeatable tests without faucet limits. Its accounts, balances and state belong to that isolated chain. Never reuse development private keys for real funds.
