package com.example.demo.repository;

import com.example.demo.entity.MedicationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MedicationPlanRepository extends JpaRepository<MedicationPlan, UUID> {

    @Query("SELECT mp FROM MedicationPlan mp JOIN FETCH mp.medication WHERE mp.user.id = :userId")
    List<MedicationPlan> findAllByUserIdWithMedication(@Param("userId") UUID userId);

    List<MedicationPlan> findByUserIdAndIsRecommendedTrue(UUID userId);
}
