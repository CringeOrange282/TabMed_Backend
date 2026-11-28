package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "medications", schema = "public",
        uniqueConstraints = @UniqueConstraint(
        name = "uq_medication_full",
        columnNames = {"name", "manufacturer", "tablet_form"}
))
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "manufacturer", length = 100, nullable = false)
    private String manufacturer;

    @Column(name = "tablet_form", length = 50, nullable = false)
    private String tabletForm;
    public Medication() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setTabletForm(String tablet_form) {
        this.tabletForm = tablet_form;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getTabletForm() {
        return tabletForm;
    }
}
