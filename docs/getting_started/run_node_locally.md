# Run a node locally

Web3j connects to an Ethereum JSON-RPC endpoint. Choose a development chain for local tests, or a public-network endpoint for integration with Sepolia or Ethereum mainnet.

## Local development chain

For a single-node development chain, install [Geth](https://geth.ethereum.org/docs/getting-started/installing-geth) and use its [developer mode](https://geth.ethereum.org/docs/developers/dapp-developer/dev-mode):

```bash
geth --dev --http --http.api eth,net,web3 --http.addr 127.0.0.1 --datadir ./geth-dev-data
```

This creates an isolated development chain with no external peers and a simulated consensus client. Keep its RPC endpoint bound to localhost. The development account and funds must never be reused on a public network.

Besu users can follow the [private-network startup guide](https://docs.besu-eth.org/private-networks/get-started/start-node) and [CLI reference](https://docs.besu-eth.org/public-networks/reference/cli/options). Use `--network`, with two leading hyphens, when selecting a network.

Check that RPC is available before connecting Web3j:

```bash
curl --fail --silent --show-error http://127.0.0.1:8545 \
  -H 'Content-Type: application/json' \
  --data '{"jsonrpc":"2.0","method":"web3_clientVersion","params":[],"id":1}'
```

For a multi-client private network, [ethereum-package](https://github.com/ethpandaops/ethereum-package) provides a configurable development environment. Follow its prerequisites and quickstart to obtain the execution RPC endpoint.

## A local client on a public network

Running an Ethereum public-network node requires both an **execution client** and a **consensus client**. Configure a shared JWT secret for their authenticated connection; this is separate from the JSON-RPC endpoint used by Web3j.

Follow a complete client-pair guide for [Geth](https://geth.ethereum.org/docs/getting-started), [Besu](https://docs.besu-eth.org/public-networks/get-started/start-node) or [Nethermind](https://docs.nethermind.io/get-started/running-node/). Select Sepolia for application testing and allow the node to synchronize. Starting only an execution-client process does not establish a working public-network node.

Alternatively, use a hosted Sepolia RPC endpoint with the [quickstart](../quickstart.md). See [Ethereum test networks](../transactions/ethereum_testnets.md) for test ETH and network selection, and the [Ethereum JSON-RPC reference](https://ethereum.org/en/developers/docs/apis/json-rpc/) for RPC methods.
