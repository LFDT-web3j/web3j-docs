## Transaction types

The different types of transaction in Web3j work with both Transaction and RawTransaction objects. The key difference is that Transaction objects must always have a from address, so that the Ethereum client
which processes the [eth_sendTransaction](https://ethereum.org/en/developers/docs/apis/json-rpc/#eth_sendtransaction) request know which wallet to use in order to sign and send the
transaction on the message senders behalf. As mentioned [above](transaction_mechanisms.md#offline-transaction-signing), this is not necessary for raw transactions which are signed offline.

The subsequent sections outline the key transaction attributes required for the different transaction types. The following attributes remain constant for all:

- Gas price
- Gas limit
- Nonce
- From

Transaction and RawTransaction objects are used interchangeably in all of the subsequent examples.

Web3j supports newer typed transactions including EIP-7702 delegated smart account transactions. For details, refer to [EIP-7702 Transaction](EIP_transaction_types/eip7702_transaction.md).
