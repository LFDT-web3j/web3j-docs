package org.web3j.docs;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

class ExamplesTest {
    @Test
    void readsBalanceThroughJsonRpc() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse().setHeader("Content-Type", "application/json")
                    .setBody("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0xde0b6b3a7640000\"}"));
            server.start();
            Web3j web3j = Web3j.build(new HttpService(server.url("/").toString()));
            try {
                assertEquals(new BigInteger("1000000000000000000"),
                        ReadOnlyExample.balance(web3j, "0x0000000000000000000000000000000000000001"));
                var request = server.takeRequest(5, TimeUnit.SECONDS);
                assertNotNull(request);
                String body = request.getBody().readUtf8();
                assertTrue(body.contains("eth_getBalance"));
                assertTrue(body.contains("latest"));
            } finally {
                web3j.shutdown();
            }
        }
    }

    @Test
    void reportsNodeErrors() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse().setHeader("Content-Type", "application/json")
                    .setBody("{\"jsonrpc\":\"2.0\",\"id\":1,\"error\":{\"code\":-32000,\"message\":\"unavailable\"}}"));
            server.start();
            Web3j web3j = Web3j.build(new HttpService(server.url("/").toString()));
            try {
                IOException error = assertThrows(IOException.class,
                        () -> ReadOnlyExample.balance(web3j, "0x0000000000000000000000000000000000000001"));
                assertTrue(error.getMessage().contains("unavailable"));
            } finally {
                web3j.shutdown();
            }
        }
    }

    @Test
    void authorizationRecoversItsSigner() throws Exception {
        // Public test key only; never use it to hold funds.
        Credentials authority = Credentials.create("1");
        var authorization = AuthorizationExample.authorize(authority,
                BigInteger.valueOf(11155111), "0x0000000000000000000000000000000000000001",
                BigInteger.ZERO);
        assertEquals(authority.getAddress(), Sign.recoverAuthorizationSigner(authorization));
    }
}
