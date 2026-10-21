package org.web3j.docs;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.SignedRawTransaction;
import org.web3j.crypto.TransactionDecoder;
import org.web3j.crypto.transaction.type.Transaction1559;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ObjectMapperFactory;

class RawTransactionExampleTest {
    private static final String RECIPIENT = "0x0000000000000000000000000000000000000001";

    private String signedTransfer() {
        // Public test key only; never use it to hold funds.
        return RawTransactionExample.signEther(Credentials.create("1"), 11155111L,
                BigInteger.valueOf(7), BigInteger.valueOf(21000), RECIPIENT,
                BigInteger.valueOf(123), BigInteger.valueOf(2_000_000_000L),
                BigInteger.valueOf(30_000_000_000L));
    }

    @Test
    void signsAnEip1559TransferForTheIntendedChainAndAccount() throws Exception {
        String encoded = signedTransfer();
        assertTrue(encoded.startsWith("0x02"));
        var decoded = (SignedRawTransaction) TransactionDecoder.decode(encoded);
        var transaction = (Transaction1559) decoded.getTransaction();
        assertEquals(Credentials.create("1").getAddress(), decoded.getFrom());
        assertEquals(11155111L, transaction.getChainId());
        assertEquals(BigInteger.valueOf(7), transaction.getNonce());
        assertEquals(BigInteger.valueOf(21000), transaction.getGasLimit());
        assertEquals(RECIPIENT, transaction.getTo());
        assertEquals(BigInteger.valueOf(123), transaction.getValue());
        assertEquals(BigInteger.valueOf(2_000_000_000L), transaction.getMaxPriorityFeePerGas());
        assertEquals(BigInteger.valueOf(30_000_000_000L), transaction.getMaxFeePerGas());
    }

    @Test
    void submitsOnlyTheSignedBytesAndReturnsTheNodeHash() throws Exception {
        String signed = signedTransfer();
        String hash = "0x" + "ab".repeat(32);
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse().setHeader("Content-Type", "application/json")
                    .setBody("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"" + hash + "\"}"));
            server.start();
            Web3j web3j = Web3j.build(new HttpService(server.url("/").toString()));
            try {
                assertEquals(hash, RawTransactionExample.submit(web3j, signed));
                var request = server.takeRequest(5, TimeUnit.SECONDS);
                assertNotNull(request);
                var json = ObjectMapperFactory.getObjectMapper().readTree(request.getBody().readUtf8());
                assertEquals("eth_sendRawTransaction", json.get("method").asText());
                assertEquals(1, json.get("params").size());
                assertEquals(signed, json.get("params").get(0).asText());
            } finally {
                web3j.shutdown();
            }
        }
    }

    @Test
    void surfacesRejectedTransactions() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse().setHeader("Content-Type", "application/json")
                    .setBody("{\"jsonrpc\":\"2.0\",\"id\":1,\"error\":{\"code\":-32000,\"message\":\"nonce too low\"}}"));
            server.start();
            Web3j web3j = Web3j.build(new HttpService(server.url("/").toString()));
            try {
                IOException error = assertThrows(IOException.class,
                        () -> RawTransactionExample.submit(web3j, signedTransfer()));
                assertTrue(error.getMessage().contains("nonce too low"));
            } finally {
                web3j.shutdown();
            }
        }
    }
}
