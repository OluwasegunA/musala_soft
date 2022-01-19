package com.oluwasegunariyo.drone.repository;

import com.oluwasegunariyo.drone.model.medicationModel.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    //Optional<Category> findCategoryByName(String name);
}
