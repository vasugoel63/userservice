package org.example.controller;

import org.example.entities.RefreshToken;
import org.example.request.AuthRequestDTO;
import org.example.response.JwtResponseDTO;
import org.example.request.RefreshTokenDTO;
import org.example.services.JwtService;
import org.example.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;

@Controller
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshtokenService;

    @Autowired
    private JwtService jwtService;

    // here we are creating both tokens - jwt and refresh as user is login up in
    // this case
    @PostMapping("auth/v1/login")
    public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUsername(),
                        authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshtokenService.createRefreshToken(authRequestDTO.getUsername());
            return new ResponseEntity<>(JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Invalid credentials",
                    HttpStatus.UNAUTHORIZED);
        }
    }

    // refresh token is ok but jwt expired here
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {

        return refreshtokenService.findByToken(refreshTokenDTO.getToken())
                .map(refreshtokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());

                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenDTO.getToken())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh Token is not in DB"));
    }
}