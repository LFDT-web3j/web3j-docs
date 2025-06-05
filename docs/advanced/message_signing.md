Message Signing
===============

Web3j can sign arbitrary-sized messages in the `personal_sign` style (see [EIP-191](https://eips.ethereum.org/EIPS/eip-191)), via the `signPrefixedMessage` method of the `Sign` class. This allows you to sign a message, obtain the signature as a `SignatureData` type object (containing the `r`, `s` and `v` components of the signature), and recover the signer's public key or address from the signature and the original message.

## Usage

Here is a complete usage example:

```java
// Create a signer from a private key
Credentials credentials = Credentials.create("<private key>");

// Sign the message
String message = "The Times 03/Jan/2009 Chancellor on brink of second bailout for banks";
SignatureData signatureData = Sign.signPrefixedMessage(message.getBytes(StandardCharsets.UTF_8), credentials.getEcKeyPair());

// Recover the signer's public key and convert it to address
BigInteger ecPubKey = Sign.signedPrefixedMessageToKey(message.getBytes(), signatureData);
String address = Numeric.prependHexPrefix(Keys.getAddress(ecPubKey));

// Check if the signer’s address matches the recovered address
address.equals(credentials.getAddress()); // true
```

### Obtain signature hex string

By concatenating the components of the signature from the `signatureData` object, you can obtain the signature as a hexadecimal string as follows:

```java
byte[] signature = new byte[65];
System.arraycopy(signatureData.getR(), 0, signature, 0, 32);
System.arraycopy(signatureData.getS(), 0, signature, 32, 32);
System.arraycopy(signatureData.getV(), 0, signature, 64, 1);

Numeric.toHexString(signature); // "0x..."
```