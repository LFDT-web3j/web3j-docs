Ethereum Name Service
=====================

The [Ethereum Name Service (ENS)](https://ens.domains) provides a human readable names to identify addresses on the Ethereum network. It is similar to the internet's domain name service (DNS) which provides human-readable domain names which are mapped to IP addresses.

In the case of ENS, the addresses are either wallet or smart contract addresses.

E.g. instead of using the wallet address `0x19e03255f667bdfd50a32722df860b1eeaf4d635`, you can use `web3j.eth`.

Usage in web3j
--------------

You can use ENS names anywhere you wish to transact in web3j. In practice this means, in smart contract wrappers, when you load them, such as:

```java
YourSmartContract contract = YourSmartContract.load(
        "0x<address>|<ensName>", web3j, credentials, contractGasProvider);
```

Also, when performing Ether transfers, such as using the command line
tools:

```bash
$ web3j wallet send <walletfile> 0x<address>|<ensName>
```

ENS Features Supported with Code Examples
-----------------------------------------

#### ENS is supported for these chains - 

- Ethereum Mainnet
- Sepolia Testnet
- Holesky Testnet
- Linea Mainnet
- Linea Sepolia Testnet

#### Code Examples -

- Forward resolution from ENS to address:

```java 
Web3j web3j = Web3j.build(new HttpService("<rpc_endpoint_url>"));
EnsResolver ensResolver = new EnsResolver(web3j);

String ensName = ENSNormalize.ENSIP15.normalize("nick.eth");
System.out.println("ENS address = " + ensResolver.resolve(ensName));
```

- Reverse Resolution from address to Primary ENS:

```java 
// 0x225f137127d9067788314bc7fcc1f36746a3c3B5 -> luc.eth
String ensPrimaryName = ensResolver.reverseResolve("0x225f137127d9067788314bc7fcc1f36746a3c3B5");
```

- Set primary ENS name for any address:

```java 
Credentials credentials = Credentials.create("<private_key>");

// Set Primary Name
TransactionReceipt receiptResult1 = ensResolver.setReverseName("nick.eth", credentials);

// Set Primary Name for Contract (or Address)
// for address = 0x225f137127d9067788314bc7fcc1f36746a3c3B5
// owner address (function caller) = credentials.getAddress()
// resolver address = 0x9010A27463717360cAD99CEA8bD39b8705CCA238
TransactionReceipt receiptResult2 = ensResolver.setReverseName(
        "0x225f137127d9067788314bc7fcc1f36746a3c3B5",
        credentials.getAddress(),
        "0x9010A27463717360cAD99CEA8bD39b8705CCA238",
        "nick.eth",
        credentials
    );
```

- Get nameHash and labelHash for ENS:

```java 
// ENS name: luc.eth
String nameHashString = NameHash.nameHash("luc.eth");
byte[] nameHash = NameHash.nameHashAsBytes("luc.eth");

// ENS label: luc
String labelHashString = NameHash.nameHash("luc");
byte[] labelHash = NameHash.nameHashAsBytes("luc");

// DNS Encoded Name
String dnsEncodedName = NameHash.dnsEncode("name.eth");
```

- Getting and setting ENS records:

```java 
// Get ENS text
String url = ensResolver.getEnsText("nick.eth", "url");

// Set ENS text
TransactionReceipt receiptResult3 = ensResolver.setEnsText("nick.eth", "url", "http://example.com", credentials);
```

- Get Owner and Resolver address for any ENS name:

```java 
String resolver = ensResolver.getResolverAddress("luc.eth");
String owner = ensResolver.getOwnerAddress("luc.eth");
```


Web3j implementation 
--------------------

Behind the scenes, whenever you using web3j's transaction managers (which are derived from the
[ManagedTransaction](https://github.com/web3j/web3j/blob/master/core/src/main/java/org/web3j/tx/ManagedTransaction.java) class), the [EnsResolver](https://github.com/web3j/web3j/blob/master/core/src/main/java/org/web3j/ens/EnsResolver.java) is invoked to perform an ENS lookup if applicable.

The resolution process is as follows:

-   Check to see if our Ethereum node is fully synced
-   If not fail
-   If it is synced, check the timestamp on the most recent block it has.

    - If it's more than 3 minutes old, fail.
    - Otherwise perform the lookup

If you need to change the threshold parameter of what constitutes being synced to something other then 3 minutes, this can be done via the `setSyncThreshold` method in the [ManagedTransaction](https://github.com/web3j/web3j/blob/master/core/src/main/java/org/web3j/tx/ManagedTransaction.java) class.

Unicode Technical Standard (UTS) \#46
-------------------------------------

UTS #46 is the standard used to sanitise input on domain names. The web3j ENS implementation performs this sanitation on all inputs before attempting resolution. For details of the implementation, refer to the [NameHash](https://github.com/web3j/web3j/blob/master/core/src/main/java/org/web3j/ens/NameHash.java) class.

Registering domain names
------------------------

Web3j does not support the registration of ENS name, although this is possible in Web3j using loading ENS contract but it requires manual calling. For instructions on how to do this, refer to the ENS [documentation](https://docs.ens.domains/).

