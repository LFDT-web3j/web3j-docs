package org.web3j.docs;

import java.io.IOException;
import java.math.BigInteger;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

public final class ReadOnlyExample {
    public static BigInteger balance(Web3j web3j, String address) throws IOException {
        EthGetBalance response = web3j.ethGetBalance(
                address, DefaultBlockParameterName.LATEST).send();
        if (response.hasError()) {
            throw new IOException("RPC error: " + response.getError().getMessage());
        }
        return response.getBalance();
    }

    public static void main(String[] args) throws IOException {
        String rpcUrl = System.getenv("WEB3J_RPC_URL");
        if (rpcUrl == null || rpcUrl.isBlank() || args.length != 1) {
            throw new IllegalArgumentException(
                    "Set WEB3J_RPC_URL and pass an Ethereum address as the argument.");
        }
        Web3j web3j = Web3j.build(new HttpService(rpcUrl));
        try {
            System.out.println("Balance in wei: " + balance(web3j, args[0]));
        } finally {
            web3j.shutdown();
        }
    }
}
