package com.example.demo.repository;

import com.example.demo.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

    @Repository
    public interface DeviceRepository extends JpaRepository<Device, UUID>{
        Optional<Device> findByDeviceNameAndUserId(String deviceName,UUID user_Id);

        Optional<Device> findByLastSession(String token);
    }

