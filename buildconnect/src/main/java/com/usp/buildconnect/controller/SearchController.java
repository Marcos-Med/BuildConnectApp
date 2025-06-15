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
@RequestMapping("/Search")
public class SearchController {
	
	@Autowired
	private ProfessionalsService searchService;
	
	@GetMapping
	public ResponseEntity<?> search(@RequestParam("query") String query){
		List<ProfessionalDTO> list = searchService.search(query);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
}
