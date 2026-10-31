package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "devices", schema = "public")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "device_name", length = 100, nullable = false)
    private String deviceName;

    @Column(name = "last_session")
    private OffsetDateTime lastSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Device() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public OffsetDateTime getLastSession() { return lastSession; }
    public void setLastSession(OffsetDateTime lastSession) { this.lastSession = lastSession; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
