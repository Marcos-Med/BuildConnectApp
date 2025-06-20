package com.usp.buildconnect.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.usp.buildconnect.dto.InteractionCreateDTO;
import com.usp.buildconnect.dto.InteractionDTO;
import com.usp.buildconnect.dto.InteractionUpdateDTO;
import com.usp.buildconnect.entity.IdInteraction;
import com.usp.buildconnect.entity.Interaction;
import com.usp.buildconnect.services.InteractionsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Interactions")
public class InteractionController {
	@Autowired
	private InteractionsService interactionsService;
	
	@GetMapping("/ByID")
	public ResponseEntity<?> getInteractionByID(@RequestParam("client_id") Long client_id,
			@RequestParam("post_id") Long post_id) {
		IdInteraction id = new IdInteraction();
		id.setClient_id(client_id);
		id.setPost_id(post_id);
		InteractionDTO dto = interactionsService.getInteractionById(id);
		return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
	}
	
	@GetMapping("/ByClient")
	public ResponseEntity<?> getInteractionsByClient(@RequestParam("client_id") Long client_id){
		List<InteractionDTO> list = interactionsService.getInteractionsByClient(client_id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByPost")
	public ResponseEntity<?> getInteractionsByPost(@RequestParam("post_id") Long post_id){
		List<InteractionDTO> list = interactionsService.getInteractionsByPost(post_id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<Void> createInteraction(@RequestBody @Valid InteractionCreateDTO dto){
		Interaction i = interactionsService.createInteraction(dto);
		return ResponseEntity.created(URI.create("/interactions/" + i.getId().getClient_id() +
				":" + i.getId().getPost_id())).build();
	}
	
	@PutMapping("/ByID")
	public ResponseEntity<Void> updateInteraction(@RequestBody @Valid InteractionUpdateDTO dto,
			 @RequestParam("client_id") Long client_id, @RequestParam("post_id") Long post_id){
		IdInteraction id = new IdInteraction();
		id.setClient_id(client_id);
		id.setPost_id(post_id);
		interactionsService.updateInteraction(id, dto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/ByID")
	public ResponseEntity<Void> deleteInteraction(@RequestParam("client_id") Long client_id,
			@RequestParam("post_id") Long post_id){
		IdInteraction id = new IdInteraction();
		id.setClient_id(client_id);
		id.setPost_id(post_id);
		interactionsService.deleteInteraction(id);
		return ResponseEntity.noContent().build();
	}
}
