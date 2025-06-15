package com.usp.buildconnect.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.usp.buildconnect.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByContractClientId(Long client_id);
	List<Notification> findByContractProfessionalId(Long professional_id);
}
