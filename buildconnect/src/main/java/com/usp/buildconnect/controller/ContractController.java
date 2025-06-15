package com.usp.buildconnect.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.usp.buildconnect.dto.ContractCreateDTO;
import com.usp.buildconnect.dto.ContractDTO;
import com.usp.buildconnect.entity.Contract;
import com.usp.buildconnect.services.ContractsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Contracts")
public class ContractController {
	
	@Autowired
	private ContractsService contractsService;
	
	@GetMapping("/ByID")
	public ResponseEntity<?> getContractById(@RequestParam("id") Long id) {
		ContractDTO dto = contractsService.getContractsById(id);
		return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
	}
	
	@GetMapping("/ByClient")
	public ResponseEntity<?> getContractsByClient(@RequestParam("client_id") Long id){
		List<ContractDTO> list = contractsService.getContractsByClient(id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByProfessional")
	public ResponseEntity<?> getContractsByProfessional(@RequestParam("professional_id") Long id){
		List<ContractDTO> list = contractsService.getContractsByProfessional(id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<Void> createContract(@RequestBody @Valid ContractCreateDTO dto){
		Contract contract = contractsService.createContract(dto);
		return ResponseEntity.created(URI.create("/contracts/" + contract.getId())).build();
	}
	
	@PutMapping("/Accept")
	public ResponseEntity<Void> acceptContract(@RequestParam("id") Long id){
		contractsService.updateContract("Em andamento", id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/Cancel")
	public ResponseEntity<Void> cancelContract(@RequestParam("id") Long id){
		contractsService.updateContract("Cancelado", id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/Finish")
	public ResponseEntity<Void> finishContract(@RequestParam("id") Long id){
		contractsService.updateContract("Concluído", id);
		return ResponseEntity.noContent().build();
	}
}
