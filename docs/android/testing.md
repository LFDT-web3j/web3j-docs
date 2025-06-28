# Testing with Web3j-Android

## Overview

Testing your Ethereum-based Android application is an essential step in ensuring its stability, functionality, and blockchain interaction capabilities. This guide explains how to test your app effectively with **web3j-android**, focusing on local blockchain environments, tools, and best practices.

## Setting Up a Local Ethereum Node

A local Ethereum node allows you to simulate the blockchain environment for development and testing without interacting with the actual mainnet or testnets.

### Using Ganache

Ganache is a popular tool for setting up a local Ethereum blockchain. It provides:

- Instant mining for testing transactions
- Pre-funded test accounts
- A user-friendly interface for inspecting the blockchain state

### Steps to Set Up Ganache

1. **Download and Install Ganache**

   - Visit [Truffle Suite](https://trufflesuite.com/ganache/) to download and install Ganache on your development machine.

2. **Run Ganache**

   - Start Ganache and note the RPC URL (default: `http://127.0.0.1:7545`).
   - Take note of the pre-funded accounts and their private keys for testing.

3. **Update Your App Configuration**
   - Point your `web3j` instance to the local Ganache RPC URL:
     ```java
     Web3j web3j = Web3j.build(new HttpService("http://10.0.2.2:7545"));
     ```
     > **Note**: Use `10.0.2.2` instead of `127.0.0.1` when testing in an Android emulator.

## Writing Test Cases

### Testing Connectivity

Ensure your app can connect to the local blockchain:

```java
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class TestConnection {
    public static void main(String[] args) throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://10.0.2.2:7545"));
        String clientVersion = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        System.out.println("Connected to Ethereum client: " + clientVersion);
    }
}
```

### Testing Account Balance

Verify the Ether balance of a test account:

```java
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;
import java.math.BigDecimal;

public class TestBalance {
    public static void main(String[] args) throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://10.0.2.2:7545"));
        String address = "0xYourTestAccountAddress";
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        BigDecimal etherBalance = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER);
        System.out.println("Account Balance: " + etherBalance + " ETH");
    }
}
```

### Testing Transactions

Simulate sending a transaction using a test account:

```java
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import java.math.BigDecimal;

public class TestTransaction {
    public static void main(String[] args) throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://10.0.2.2:7545"));
        Credentials credentials = Credentials.create("YourPrivateKey");
        TransactionReceipt receipt = Transfer.sendFunds(
            web3j, credentials, "0xRecipientAddress", BigDecimal.valueOf(0.01), Convert.Unit.ETHER
        ).send();
        System.out.println("Transaction complete: " + receipt.getTransactionHash());
    }
}
```

## Resources for Testing

- [Ganache Documentation](https://trufflesuite.com/docs/ganache/)
- [JUnit for Java Testing](https://junit.org/)
- [web3j-android GitHub Repository](https://github.com/hyperledger-web3j/web3j/tree/web3j-android)
