package org.example.controller;

import org.example.entities.RefreshToken;
import org.example.models.UserInfoDTO;
import org.example.response.JwtResponseDTO;
import org.example.services.JwtService;
import org.example.services.RefreshTokenService;
import org.example.services.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailServiceImp userDetailsService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity Signup(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            Boolean isSignUp = userDetailsService.signupUser(userInfoDTO);
            if (Boolean.FALSE.equals(isSignUp)) {
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }

            String jwtToken = jwtService.GenerateToken(userInfoDTO.getUsername());
            RefreshToken refreshtoken = refreshTokenService.createRefreshToken(userInfoDTO.getUsername());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).token(refreshtoken.getToken())
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}