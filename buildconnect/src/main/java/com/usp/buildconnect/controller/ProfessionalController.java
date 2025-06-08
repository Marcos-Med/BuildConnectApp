package com.usp.buildconnect.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.usp.buildconnect.dto.ProfessionalDTO;
import com.usp.buildconnect.services.SearchService;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {
	@Autowired
	private SearchService searchService;
	
	@GetMapping("/location")
	public List<ProfessionalDTO> getProfessionalsByLocation(@RequestParam("region") String region){
		return searchService.listProfessionalByRegion(region);
	}
	
	@GetMapping("/service")
	public List<ProfessionalDTO> getProfessionalByService(@RequestParam("service") String service){
		return searchService.listProfessionalByService(service);
	}
	
	@GetMapping("/name")
	public List<ProfessionalDTO> getProfessionalByName(@RequestParam("name") String name){
		return searchService.listProfessionalByName(name);
	}
	
	@GetMapping("/id")
	public ProfessionalDTO getProfessionalByID(@RequestParam("id") Long Id) {
		return searchService.getProfessionalByID(Id);
	}
}
