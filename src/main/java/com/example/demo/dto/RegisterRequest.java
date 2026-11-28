package com.example.demo.dto;

import com.example.demo.entity.Role;

public record RegisterRequest(String firstName, String secondName, String email, String password, Role role) {}
