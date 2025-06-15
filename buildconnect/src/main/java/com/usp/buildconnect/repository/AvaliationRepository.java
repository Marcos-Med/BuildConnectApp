package com.usp.buildconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usp.buildconnect.entity.Avaliation;
import com.usp.buildconnect.entity.IdAvaliation;

public interface AvaliationRepository extends JpaRepository<Avaliation, IdAvaliation> {
	List<Avaliation> findByClientId(Long client_id);
	List<Avaliation> findByProfessionalId(Long professional_id);
}
