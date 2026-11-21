package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;


    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (!user.getPassword().equals(request.getPassword())){
            throw new RuntimeException("incorrect password");
        }
        return new AuthResponse(user.getFirstName(),user.getSecondName(),user.getId(),user.getEmail(),user.getRole());
    }
}
