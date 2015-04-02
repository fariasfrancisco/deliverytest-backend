package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by francisco on 25/03/15.
 */
@Entity
public class RestauranteVerificationToken extends ModelEntity {
    private static final int DEFAULT_EXPIRATION_TIME_IN_HOURS = 24; // 1 day

    @Column(length = 36)
    private final String token;

    @Type(type = "com.safira.common.LocalDateTimeUserType")
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean verified;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantelogin_id", nullable = false)
    private RestauranteLogin restauranteLogin;

    public RestauranteVerificationToken() {
        super();
        this.token = UUID.randomUUID().toString();
        this.expiryDate = calculateExpiryDate(DEFAULT_EXPIRATION_TIME_IN_HOURS);
    }

    public RestauranteVerificationToken(RestauranteLogin restauranteLogin,
                                        TokenType tokenType,
                                        int expirationTimeInHours) {
        this();
        this.restauranteLogin = restauranteLogin;
        this.tokenType = tokenType;
        this.expiryDate = calculateExpiryDate(expirationTimeInHours);
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }

    public void setRestauranteLogin(RestauranteLogin restauranteLogin) {
        this.restauranteLogin = restauranteLogin;
    }

    private LocalDateTime calculateExpiryDate(int expirationTimeInHours) {
        return LocalDateTime.now().plusMinutes(expirationTimeInHours);
    }

    public boolean hasExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
