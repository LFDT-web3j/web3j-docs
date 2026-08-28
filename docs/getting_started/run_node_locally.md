Web3j interacts with Ethereum client nodes.
If you don't want to connect to one of the existing networks,
you have different options to start a local node.

## Use Geth, Besu or Nethermind

[Geth](https://geth.ethereum.org/docs/getting-started):

``` bash
$ geth --rpcapi personal,db,eth,net,web3 --rpc --testnet
```

[Hyperledger Besu](https://besu.hyperledger.org/en/stable/HowTo/Get-Started/Starting-node/#run-a-node-for-testing):

``` bash
$ besu ----network=dev
```

The `dev` network uses has [some handy default parameters](https://besu.hyperledger.org/en/stable/Reference/Accounts-for-Testing/#development-mode).

[Nethermind](https://docs.nethermind.io/get-started/running-node/):

``` bash
$ nethermind -c sepolia --data-dir path/to/data/dir --jsonrpc-jwtsecretfile path/to/jwt.hex
```

## Use ethereum-package

[ethereum-package](https://github.com/ethpandaops/ethereum-package) is a standard way to spin up a local private blockchain for development and testing. It can also be used to run a local mainnet fork, which makes it useful for testing Web3j integrations against realistic chain state.

Instructions on obtaining Ether to transact on the network can be found in the
[testnet section of the docs](` https://docs.web3j.io/transactions/#ethereum-testnets`).
