package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.OffsetDateTime;

@Entity
@Table(name = "medication_history", schema = "public")
public class MedicationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "taken_at", nullable = false)
    private OffsetDateTime takenAt;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private MedicationPlan plan;

    public MedicationHistory() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public OffsetDateTime getTakenAt() { return takenAt; }
    public void setTakenAt(OffsetDateTime takenAt) { this.takenAt = takenAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public MedicationPlan getPlan() { return plan; }
    public void setPlan(MedicationPlan plan) { this.plan = plan; }
}
