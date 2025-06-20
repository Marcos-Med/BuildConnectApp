package com.usp.buildconnect.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.usp.buildconnect.dto.ProfessionalDTO;
import com.usp.buildconnect.services.ProfessionalsService;

@RestController
@RequestMapping("/Professional")
public class ProfessionalController {
	@Autowired
	private ProfessionalsService searchService;
	
	@GetMapping("/ByLocation")
	public ResponseEntity<?> getProfessionalsByLocation(@RequestParam("city") String city){
		List<ProfessionalDTO> list = searchService.listProfessionalByCity(city);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByService")
	public ResponseEntity<?> getProfessionalByService(@RequestParam("service") String service){
		List<ProfessionalDTO> list = searchService.listProfessionalByService(service);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByName")
	public ResponseEntity<?> getProfessionalByName(@RequestParam("name") String name){
		List<ProfessionalDTO> list = searchService.listProfessionalByName(name);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByID")
	public ResponseEntity<?> getProfessionalByID(@RequestParam("id") Long Id) {
		ProfessionalDTO dto = searchService.getProfessionalByID(Id);
		return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
	}

	
}
