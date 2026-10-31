package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.entity.User;
import com.example.demo.entity.Device;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public AuthController(UserRepository userRepository, DeviceRepository deviceRepository) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            String fio = body.get("fio");
            String deviceName = body.get("deviceName");

            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body("Email уже занят!");
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFio(fio);
            user.setRole("ROLE_PATIENT");
            User savedUser = userRepository.save(user);

            Device device = new Device();
            device.setDeviceName(deviceName);
            device.setUser(savedUser);
            device.setLastSession(ZonedDateTime.now().toOffsetDateTime());
            deviceRepository.save(device);

            String mockToken = UUID.randomUUID().toString();

            return ResponseEntity.ok("Успех! Данные в БД. Ваш токен: " + mockToken);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка на сервере: " + e.getMessage());
        }
    }
}
