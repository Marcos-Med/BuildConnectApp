package com.usp.buildconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usp.buildconnect.entity.Professional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
}
