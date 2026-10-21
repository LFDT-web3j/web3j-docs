Troubleshooting
===============

Do you have a sample web3j project
----------------------------------

Yes, refer to the web3j sample project outlined in the [Quickstart](../quickstart.md).

My transaction has no receipt
-----------------------------

A transaction hash means the node accepted the submission, not that execution succeeded. Check the RPC response for errors, confirm the network, and query the receipt:

```java
EthGetTransactionReceipt response = web3j.ethGetTransactionReceipt(transactionHash).send();
if (response.hasError()) {
    throw new IOException(response.getError().getMessage());
}
Optional<TransactionReceipt> receipt = response.getTransactionReceipt();
if (receipt.isPresent()) {
    boolean succeeded = receipt.get().isStatusOK();
    // Handle execution success or failure.
}
```

Import the response types from `org.web3j.protocol.core.methods.response`, `java.io.IOException` and `java.util.Optional`. A missing receipt may mean the transaction is pending, was replaced, or was dropped. Use a bounded polling policy. Inspect the sender's [nonce](../transactions/transaction_nonce.md), balance, and [fees](../transactions/gas.md), and check an explorer for the same network, such as [Sepolia Etherscan](https://sepolia.etherscan.io/). Receipt availability is separate from finality; applications should also define their confirmation policy.

I want to see details of the JSON-RPC requests and responses
------------------------------------------------------------

web3j uses the [SLF4J](https://www.slf4j.org/) logging facade, which you can easily integrate with your preferred logging framework. One lightweight approach is to use [LOGBack](https://logback.qos.ch/), which is already configured in the integration-tests module.

Include the LOGBack dependencies listed in [integration-tests/build.gradle](https://github.com/LFDT-web3j/web3j/blob/v6.0.0/integration-tests/build.gradle#L7) and associated log configuration as per [integration-tests/src/test/resources/logback-test.xml](https://github.com/LFDT-web3j/web3j/blob/v6.0.0/integration-tests/src/test/resources/logback-test.xml).

**Note:** if you are configuring logging for an application (not tests), you will need to ensure that the Logback dependencies are configured as runtime dependencies, and that the configuration file is named and
located in *src/main/resources/logback.xml*.

Where can I obtain test ETH?
---------------------------------------------------------------------------------

Please refer to the [Ethereum testnets](../transactions/ethereum_testnets.md) for how to obtain some Ether.

How do I obtain the return value from a smart contract method invoked by a transaction?
---------------------------------------------------------------------------------------

You can't. It is not possible to return values from methods on smart contracts that are called as part of a transaction. If you wish to read a value during a transaction, you must use [Events](http://solidity.readthedocs.io/en/develop/contracts.html#events). To query values from smart contracts you must use a call, which is separate to a transaction. These methods should be marked as [view or pure](https://docs.soliditylang.org/en/latest/contracts.html#view-functions) functions. [Solidity smart contract wrappers](../smart_contracts/construction_and_deployment.md#solidity-smart-contract-wrappers) created by web3j handle these differences for you.

See the [Ethereum JSON-RPC reference for eth_call](https://ethereum.org/en/developers/docs/apis/json-rpc/#eth_call) for read-only execution.

Is it possible to send arbitrary text with transactions?
--------------------------------------------------------

Yes it is. Text should be ASCII encoded and provided as a hexadecimal String in the data field of the transaction. This is demonstrated below:

```java
RawTransaction.createTransaction(
        <nonce>, GAS_PRICE, GAS_LIMIT, "0x<address>", <amount>, "0x<hex encoded text>");

byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
String hexValue = Numeric.toHexString(signedMessage);

EthSendTransaction ethSendTransaction =
        web3j.ethSendRawTransaction(hexValue).send();
String transactionHash = ethSendTransaction.getTransactionHash();
...
```

*Note*: Please ensure you increase the gas limit on the transaction to allow for the storage of text.

The Ethereum [transaction reference](https://ethereum.org/en/developers/docs/transactions/) explains the input data field.

My generated contract wrapper has no deployment bytecode
--------------------------------------------------------

Check the Solidity compiler's diagnostics and the `.bin` file supplied to the wrapper generator. Interfaces and abstract contracts cannot be deployed directly; select a concrete implementation and compile it with the intended compiler version. See [compiling Solidity](../smart_contracts/compiling_solidity.md) and [contract generation](../smart_contracts/construction_and_deployment.md).

My ENS lookups are failing
--------------------------

Are you sure that you are connecting to the correct network to perform the lookup?

If web3j is telling you that the node is not in sync, you may need to change the *syncThreshold* in the
[ENS resolver](../advanced/ethereum_name_service.md#web3j-implementation).

Where can I get commercial support for web3j?
---------------------------------------------

Commercial support and training is available from [web3labs](https://www.web3labs.com/).
