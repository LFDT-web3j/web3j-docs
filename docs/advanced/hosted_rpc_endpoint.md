# Connecting web3j to a Hosted RPC Endpoint



When developing an application with web3j, you need an Ethereum-compatible node to read blockchain data, submit transactions, and interact with smart contracts.



You can run a node locally or connect to a hosted Remote Procedure Call (RPC) endpoint provided by an infrastructure provider.



A hosted RPC endpoint exposes blockchain functionality over a network connection. Instead of managing your own node, you configure web3j to send JSON-RPC requests to the provider's endpoint.



This guide explains how to connect web3j to a hosted RPC endpoint using HTTP. Pocket Network is used as an example, but the same approach applies to other providers that expose compatible JSON-RPC endpoints.



## Prerequisites



Before proceeding, ensure that you have:



- Java 21 or later.

- A Maven or Gradle project.

- The web3j core dependency.

- An HTTP(S) RPC endpoint for an Ethereum-compatible network.



## 1. Add the web3j dependency



For Maven, add the following dependency to your `pom.xml`:



```xml

<dependency>

   <groupId>org.web3j</groupId>

   <artifactId>core</artifactId>

   <version>5.0.3</version>

</dependency>

```



For Gradle:



```groovy

dependencies {

   implementation 'org.web3j:core:5.0.3'

}

```



Use a web3j version compatible with your project's Java version.



## 2. Obtain a hosted RPC endpoint



Choose an RPC provider that supports the blockchain network your application needs to access.



For example, Pocket Network provides public RPC endpoints for supported blockchain networks. Its Ethereum mainnet endpoint is:



```text

https://eth.api.pocket.network

```



Other providers may require you to create an account, generate an API key, or configure additional authentication.



Make sure that the endpoint corresponds to the intended blockchain network. An endpoint for Ethereum mainnet, for example, should not be used when your application is intended to interact with a testnet.



## 3. Configure web3j to use the endpoint



Web3j uses `HttpService` to communicate with nodes over HTTP.



Pass your hosted RPC endpoint to `HttpService` when creating the web3j instance:



```java

import org.web3j.protocol.Web3j;

import org.web3j.protocol.http.HttpService;



public class HostedRpcExample {



   public static void main(String[] args) {

       String rpcUrl = "https://eth.api.pocket.network";



       Web3j web3j = Web3j.build(new HttpService(rpcUrl));



       try {

           System.out.println("Web3j client initialized.");

       } finally {

           web3j.shutdown();

       }

   }

}

```



Replace the endpoint URL with the address supplied by your chosen RPC provider.



For applications that use private or authenticated endpoints, avoid hardcoding credentials in source code. Use environment variables or an appropriate secrets-management solution.



## 4. Send a JSON-RPC request



Once web3j has been configured, you can use its existing API to send requests to the connected node.



For example, the following code retrieves the latest block number:



```java

import java.math.BigInteger;



import org.web3j.protocol.Web3j;

import org.web3j.protocol.core.methods.response.EthBlockNumber;

import org.web3j.protocol.http.HttpService;



public class BlockNumberExample {



   public static void main(String[] args) throws Exception {

       String rpcUrl = "https://eth.api.pocket.network";



       Web3j web3j = Web3j.build(new HttpService(rpcUrl));



       try {

           EthBlockNumber response = web3j.ethBlockNumber().send();



           if (response.hasError()) {

               System.err.println(

                   "RPC error: " + response.getError().getMessage()

               );

               return;

           }



           BigInteger blockNumber = response.getBlockNumber();



           System.out.println("Latest block number: " + blockNumber);



       } finally {

           web3j.shutdown();

       }

   }

}

```



The `ethBlockNumber` method sends the `eth_blockNumber` JSON-RPC request to the configured endpoint. The response contains the latest block number reported by the connected network.



Other web3j methods can be used in the same way to retrieve balances, transaction information, block details, and smart contract data.



## 5. Handling endpoint configuration



For applications that need to switch between providers or blockchain networks, keep the endpoint outside the application source code.



For example, you can read the endpoint from an environment variable:



```java

String rpcUrl = System.getenv("WEB3J_RPC_URL");



if (rpcUrl == null || rpcUrl.isBlank()) {

   throw new IllegalStateException(

       "WEB3J_RPC_URL environment variable is not configured."

   );

}



Web3j web3j = Web3j.build(new HttpService(rpcUrl));

```



This makes it easier to change the RPC endpoint without modifying the application code.



If your provider requires authentication, follow its documentation for the required authentication headers or connection configuration.



## 6. Troubleshooting



If web3j cannot communicate with the hosted endpoint, consider the following:



| Issue | Possible cause | What to check |

|---|---|---|

| Connection timeout | Endpoint is unreachable or network connectivity is unavailable | Check the endpoint URL and network connection. |

| HTTP error response | Provider-side issue, access restriction, or invalid request | Check the provider's documentation and service status. |

| JSON-RPC error | Unsupported method or invalid request parameters | Verify that the method is supported by the selected network. |

| Unexpected blockchain data | Endpoint points to a different network | Confirm the endpoint's network and chain configuration. |

| Authentication error | Required credentials or headers are missing | Follow the provider's authentication instructions. |



A successful HTTP connection does not necessarily mean that every JSON-RPC method is supported. Check the capabilities and limitations of your chosen provider.



## Conclusion



Connecting web3j to a hosted RPC endpoint does not require a separate integration library for each provider. By configuring `HttpService` with a compatible endpoint, applications can use web3j's existing JSON-RPC methods to communicate with the blockchain.



The same configuration approach can be used with Pocket Network and other providers that expose compatible HTTP JSON-RPC endpoints.
