## Basic Usage

### Initialize Web3j

To interact with an Ethereum node, initialize the `Web3j` instance:

```java
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/YOUR_INFURA_PROJECT_ID"));
System.out.println("Connected to Ethereum: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
```

### Fetch Account Balance

Retrieve the Ether balance of an account:

```java
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;
import java.math.BigDecimal;

String address = "0xYourEthereumAddress";
EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
BigDecimal balance = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
System.out.println("Balance: " + balance + " ETH");
```

### Send a Transaction

Send Ether to another account:

```java
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

Credentials credentials = Credentials.create("YOUR_PRIVATE_KEY");
TransactionReceipt receipt = Transfer.sendFunds(
    web3j, credentials, "0xRecipientAddress", BigDecimal.valueOf(0.01), Convert.Unit.ETHER
).send();
System.out.println("Transaction complete: " + receipt.getTransactionHash());
```

### Note on Codegen Module

Currently, the `codegen` module is not available in the Android version. It is under active development and testing, and will be released soon.
