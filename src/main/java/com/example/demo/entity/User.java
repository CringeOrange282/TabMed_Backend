package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "fio", length = 150)
    private String fio;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "emergency_contact", length = 150)
    private String emergencyContact;

    @Column(name = "role", length = 20)
    private String role;

    public User() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFio() { return fio; }
    public void setFio(String fio) { this.fio = fio; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
