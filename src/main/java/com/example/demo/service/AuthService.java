package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, RefreshTokenRepository refreshTokenRepository
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        RefreshToken refreshToken =  refreshTokenService.generateRefreshToken(user);

        return new AuthResponse(
                user.getFirstName(),
                user.getSecondName(),
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                jwtToken,
                refreshToken.getToken()
        );
    }

    public AuthResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email уже занят");
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setFirstName(request.firstName());
        user.setSecondName(request.secondName());
        user = userRepository.save(user);

        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        RefreshToken refreshToken =  refreshTokenService.generateRefreshToken(user);
        return new AuthResponse(

                user.getFirstName(),
                user.getSecondName(),
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                jwtToken,
                refreshToken.getToken()
        );
    }

    public TokenRefreshResponse refresh(RefreshTokenRequest request){
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken()).orElseThrow(() -> new RuntimeException("Refresh-токен не найден"));
        refreshTokenService.verifyExpiration(refreshToken);
        String jwtToken = jwtService.generateToken(new CustomUserDetails(refreshToken.getUser()));
        return new TokenRefreshResponse(jwtToken, refreshToken.getToken());
    }
}
