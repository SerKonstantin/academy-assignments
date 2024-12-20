package org.academy.constants;

public final class Constants {
    private Constants() {}

    // In real app secret should be stored in env at least, or even replaced with dynamically changed rsa key pair;
    public static final String SECRET_STRING = "IJLyWUwad2eHdsouRxdoqfdKYDT1qjSx";

    // Expiration time for JWT access token, 15 minutes in milliseconds
    public static final int ACCESS_TOKEN_EXPIRATION_TIME = 900000;

    // Expiration time for JWT refresh token, 30 minutes in milliseconds
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 1800000;

    // Encode/decode algorithm for JWT signature
    public static final String ALGORITHM = "HmacSHA256";

    // User isAccountNonLocked() flag is set true after number of attempts
    public static final int MAX_SIGN_IN_FAILS_COUNT = 5;
}
