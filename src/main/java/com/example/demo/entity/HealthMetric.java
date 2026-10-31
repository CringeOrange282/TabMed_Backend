package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.time.OffsetDateTime;

@Entity
@Table(name = "health_metrics", schema = "public")
public class HealthMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "measured_at", nullable = false)
    private OffsetDateTime measuredAt;

    @Column(name = "temperature", nullable = false, precision = 4, scale = 2)
    private BigDecimal temperature;

    @Column(name = "pulse", nullable = false)
    private int pulse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public HealthMetric() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public OffsetDateTime getMeasuredAt() { return measuredAt; }
    public void setMeasuredAt(OffsetDateTime measuredAt) { this.measuredAt = measuredAt; }
    public BigDecimal getTemperature() { return temperature; }
    public void setTemperature(BigDecimal temperature) { this.temperature = temperature; }
    public int getPulse() { return pulse; }
    public void setPulse(int pulse) { this.pulse = pulse; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
