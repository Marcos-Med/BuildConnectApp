package com.usp.buildconnect.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.usp.buildconnect.dto.NotificationDTO;
import com.usp.buildconnect.services.NotificationsService;

@RestController
@RequestMapping("/Notifications")
public class NotifcationController {
	@Autowired
	private NotificationsService notificationsService;
	
	@GetMapping("/ByClient")
	public ResponseEntity<?> getNotificationsByClient(@RequestParam("client_id") Long id){
		List<NotificationDTO> list = notificationsService.getNotificationsByClient(id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByProfessional")
	public ResponseEntity<?> getNotificationsByProfessional(@RequestParam("professional_id") Long id){
		List<NotificationDTO> list = notificationsService.getNotificationsByProfessional(id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByID")
	public ResponseEntity<?> getNotificationById(@RequestParam("id") Long id) {
		NotificationDTO dto = notificationsService.getNotificationById(id);
		return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/ByID")
	public ResponseEntity<Void> deleteNotificationById(@RequestParam("id") Long id) {
		notificationsService.deleteNotification(id);
		return ResponseEntity.noContent().build();
	}
}
