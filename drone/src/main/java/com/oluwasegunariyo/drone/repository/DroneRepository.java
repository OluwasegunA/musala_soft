package com.oluwasegunariyo.drone.repository;

import com.oluwasegunariyo.drone.model.droneModel.Drones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drones, Integer> {

    //Optional<Drones> findBookByName(String name);

}
