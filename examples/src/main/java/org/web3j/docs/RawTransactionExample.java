package org.web3j.docs;

import java.io.IOException;
import java.math.BigInteger;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Numeric;

public final class RawTransactionExample {
    private RawTransactionExample() {}

    // All monetary values and fee caps are in wei. No node connection is needed here.
    public static String signEther(
            Credentials credentials, long chainId, BigInteger nonce,
            BigInteger gasLimit, String recipient, BigInteger value,
            BigInteger maxPriorityFeePerGas, BigInteger maxFeePerGas) {
        RawTransaction transaction = RawTransaction.createEtherTransaction(
                chainId, nonce, gasLimit, recipient, value,
                maxPriorityFeePerGas, maxFeePerGas);
        return Numeric.toHexString(TransactionEncoder.signMessage(transaction, credentials));
    }

    // Submission returns a hash, not a receipt or confirmation of execution.
    public static String submit(Web3j web3j, String signedTransaction) throws IOException {
        var response = web3j.ethSendRawTransaction(signedTransaction).send();
        if (response.hasError()) {
            throw new IOException("Transaction rejected: " + response.getError().getMessage());
        }
        return response.getTransactionHash();
    }
}
