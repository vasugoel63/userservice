package org.example.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.example.entities.RefreshToken;
import org.example.entities.UserInfo;
import org.example.repositories.RefreshTokenRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshtokenrepository;

    @Autowired
    UserRepository userrepository;

    public RefreshToken createRefreshToken(String username) {
        UserInfo userInfoExpected = userrepository.findByUsername(username);
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfoExpected)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(60000))
                .build();
        return refreshtokenrepository.save(refreshToken);
        // .save method store refresh token in repo with id
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshtokenrepository.delete(token);
            throw new RuntimeException(token.getToken() + "Refresh token is expired. Please make a new login");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshtokenrepository.findByToken(token);
    }

}

// repository to always called in service and not in controller