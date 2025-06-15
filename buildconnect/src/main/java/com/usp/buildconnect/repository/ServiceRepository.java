package com.usp.buildconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usp.buildconnect.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>{
}
