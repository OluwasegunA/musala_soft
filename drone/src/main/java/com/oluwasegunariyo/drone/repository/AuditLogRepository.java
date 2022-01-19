package com.oluwasegunariyo.drone.repository;

import com.oluwasegunariyo.drone.model.droneModel.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
