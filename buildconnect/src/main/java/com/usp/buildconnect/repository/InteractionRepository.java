package com.usp.buildconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usp.buildconnect.entity.IdInteraction;
import com.usp.buildconnect.entity.Interaction;

public interface InteractionRepository extends JpaRepository<Interaction, IdInteraction>{
	List<Interaction> findByClientId(Long client_id);
	List<Interaction> findByPostId(Long post_id);
}
