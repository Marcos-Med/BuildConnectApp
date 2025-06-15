package com.usp.buildconnect.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.usp.buildconnect.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long>{
	List<Contract> findByClientId(Long clientId);
	List<Contract> findByProfessionalId(Long professionalId);
}
