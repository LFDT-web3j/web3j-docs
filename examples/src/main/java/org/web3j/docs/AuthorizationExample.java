package org.web3j.docs;

import java.math.BigInteger;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.crypto.AuthorizationTuple;

public final class AuthorizationExample {
    public static AuthorizationTuple authorize(
            Credentials authority, BigInteger chainId, String delegate, BigInteger authorityNonce) {
        return Sign.signAuthorization(chainId, delegate, authorityNonce, authority);
    }
}
