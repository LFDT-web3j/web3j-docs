# Client-specific management APIs

Web3j implements the standard [Ethereum JSON-RPC API](https://ethereum.org/en/developers/docs/apis/json-rpc/). Clients may expose additional management methods, but availability depends on their version and configuration.

The `Admin`, `Geth` and `Parity` modules include legacy management methods. Geth’s [personal namespace is deprecated](https://geth.ethereum.org/docs/interacting-with-geth/rpc/ns-personal), and [OpenEthereum is archived](https://github.com/openethereum/openethereum). Do not assume that a modern node or hosted RPC provider supports account unlocking or these client-specific methods.

For new applications, use [local credentials and signing](../transactions/credentials.md), and submit signed transactions through the node’s standard RPC interface. Keep administrative endpoints off public networks.

When maintaining a legacy integration, refer to the API for the exact client and Web3j version in use. The [Admin API](https://javadoc.io/static/org.web3j/core/{{ web3j.version }}/org/web3j/protocol/admin/Admin.html) documents Web3j’s request types; it does not guarantee that the connected node implements them.
