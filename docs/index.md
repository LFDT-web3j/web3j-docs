Web3j
=====

Web3j is a highly modular, reactive, type safe Java and Android library for working with Smart Contracts and integrating with clients (nodes) on the Ethereum network:

![image](img/web3j_network.png)

This allows you to work with the [Ethereum](https://www.ethereum.org/) blockchain, without the additional overhead of having to write your own integration code for the platform.

The [Java and the Blockchain](https://www.youtube.com/watch?v=ea3miXs_P6Y) talk provides an overview of blockchain, Ethereum and web3j.

This guide covers **Web3j {{ web3j.version }} on Java 21 or newer**. Start with the [quickstart](quickstart.md), or follow [upgrading to Web3j 6](getting_started/web3j_6.md). Android uses a [separate release line](getting_started/setup_android.md).

Features
========

-   Support for Ethereum's [JSON-RPC](https://ethereum.org/en/developers/docs/apis/json-rpc/) client API over HTTP and IPC
-   Ethereum wallet support
-   Auto-generation of Java smart contract wrappers to create, deploy, transact with and call smart contracts from native Java code ([Solidity](http://solidity.readthedocs.io/en/latest/using-the-compiler.html#using-the-commandline-compiler) and [Truffle](https://github.com/trufflesuite/truffle) definition formats supported)
-   Reactive-functional API for working with filters
-   [Ethereum Name Service (ENS)](https://ens.domains/) support
-   Legacy client-specific [management APIs](advanced/management_apis.md)
-   Support for [Alchemy](https://alchemyapi.io/) and [Infura](https://infura.io/), so you don't have to run an Ethereum client yourself
-   Support for ERC20 and ERC721 token standards
-   Support for HSM
-   Comprehensive integration tests demonstrating a number of the above scenarios
-   Command line tools
-   Separate Android-compatible artifacts
-   Support for JP Morgan's Quorum via
    [web3j-quorum](https://github.com/web3j/quorum)

Dependencies
============

Key dependencies include:

-   [RxJava](https://github.com/ReactiveX/RxJava) for its reactive-functional API
-   [OkHttp](https://github.com/square/okhttp) for HTTP connections
-   [Jackson Core](https://github.com/FasterXML/jackson-core) for fast JSON serialisation/deserialization
-   [Bouncy Castle](https://www.bouncycastle.org/) for crypto
-   [Jnr-unixsocket](https://github.com/jnr/jnr-unixsocket) for \*nix IPC (not available on Android)

It also uses [JavaPoet](https://github.com/square/javapoet) for generating smart contract wrappers

Commercial support and training
===============================

Commercial support and training is available from [Web3 Labs](https://www.web3labs.com/web3j-sdk).

