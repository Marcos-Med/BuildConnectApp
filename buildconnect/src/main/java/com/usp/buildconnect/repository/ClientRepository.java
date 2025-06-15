package com.usp.buildconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usp.buildconnect.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{	
}
