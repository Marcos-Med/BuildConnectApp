package com.usp.buildconnect.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usp.buildconnect.dto.NotificationDTO;
import com.usp.buildconnect.entity.Contract;
import com.usp.buildconnect.entity.Notification;
import com.usp.buildconnect.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class NotificationsService {
	@Autowired
	private NotificationRepository notificationRepository;
	
	
	public List<NotificationDTO> getNotificationsByClient(Long id){
		List<Notification> list = notificationRepository.findByContractClientId(id);
		return mapRawResultsToDTOs(list);
	}
	
	public List<NotificationDTO> getNotificationsByProfessional(Long id){
		List<Notification> list = notificationRepository.findByContractProfessionalId(id);
		return mapRawResultsToDTOs(list);
	}
	
	public NotificationDTO getNotificationById(Long id) {
		Notification notify = notificationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Notification Not Found"));
		List<Notification> list = new ArrayList<>();
		list.add(notify);
		return mapRawResultsToDTOs(list).getFirst();
	}
	
	public void createNotification(String message, Contract contract) {
		Notification notify = new Notification();
		notify.setMessage(message);
		notify.setContract(contract);
		notificationRepository.save(notify);
	}
	
	public void deleteNotification(Long id) {
		Notification notify = notificationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Notification Not Found"));
		notificationRepository.delete(notify);
	}
	
	private List<NotificationDTO> mapRawResultsToDTOs(List<Notification> list){
		List<NotificationDTO> result = new ArrayList<>();
		for(Notification notify: list) {
			Long id = notify.getId();
			String message = notify.getMessage();
			Long contract_id = notify.getContract().getId();
			result.add(new NotificationDTO(id, message, contract_id));
		}
		return result;
	}
}
