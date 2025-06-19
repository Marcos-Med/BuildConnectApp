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
import com.usp.buildconnect.dto.AvaliationCreateDTO;
import com.usp.buildconnect.dto.AvaliationDTO;
import com.usp.buildconnect.dto.AvaliationUpdateDTO;
import com.usp.buildconnect.entity.Avaliation;
import com.usp.buildconnect.entity.IdAvaliation;
import com.usp.buildconnect.services.AvaliationsService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/Avaliations")
public class AvaliationController {
	
	@Autowired
	private AvaliationsService avaliationsService;
	
	@GetMapping("/ByProfessional")
	public ResponseEntity<?> getAvaliationsByProfessional(@RequestParam("professional_id") Long id){
		List<AvaliationDTO> list = avaliationsService.getAvaliationsByProfessional(id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByClient")
	public ResponseEntity<?> getAvaliationsByClient(@RequestParam("client_id") Long id){
		List<AvaliationDTO> list = avaliationsService.getAvaliationsByClient(id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByID")
	public ResponseEntity<?> getAvaliationById(@RequestParam("client_id") Long client_id, @RequestParam("professional_id") Long professional_id){
		IdAvaliation id = new IdAvaliation();
		id.setClient_id(client_id);
		id.setProfessional_id(professional_id);
		AvaliationDTO dto = avaliationsService.getAvaliationById(id);
		return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<Void> createAvaliation(@RequestBody @Valid AvaliationCreateDTO dto){
		Avaliation avaliation = avaliationsService.createAvaliation(dto);
		return ResponseEntity.created(URI.create("/avaliations/" + avaliation.getId().getClient_id() + ":" + 
				avaliation.getProfessional().getId())).build();
	}
	
	@PutMapping("/ByID")
	public ResponseEntity<Void> updateAvaliation(@RequestBody @Valid AvaliationUpdateDTO dto,
			@RequestParam("client_id") Long client_id, @RequestParam("professional_id") Long professional_id){
		IdAvaliation id = new IdAvaliation();
		id.setClient_id(client_id);
		id.setProfessional_id(professional_id);
		avaliationsService.updateAvaliation(id, dto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/ByID")
	public ResponseEntity<Void> deleteAvaliation(@RequestParam("client_id") Long client_id, 
			@RequestParam("professional_id") Long professional_id){
		IdAvaliation id = new IdAvaliation();
		id.setClient_id(client_id);
		id.setProfessional_id(professional_id);
		avaliationsService.deleteAvaliation(id);
		return ResponseEntity.noContent().build();
	}
}
