package com.usp.buildconnect.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usp.buildconnect.dto.ContractCreateDTO;
import com.usp.buildconnect.dto.ContractDTO;
import com.usp.buildconnect.entity.Client;
import com.usp.buildconnect.entity.Contract;
import com.usp.buildconnect.entity.Professional;
import com.usp.buildconnect.repository.ClientRepository;
import com.usp.buildconnect.repository.ContractRepository;
import com.usp.buildconnect.repository.ProfessionalRepository;
import com.usp.buildconnect.repository.ServiceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContractsService {
	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private ProfessionalRepository professionalRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private NotificationsService notificationService;
	
	private HashMap<String, Integer> mapStatus = getMapStatus();
	
	private HashMap<String, Integer> getMapStatus(){
		HashMap<String, Integer> map = new HashMap<>();
		map.put("Aguardando", 0);
		map.put("Em andamento", 1);
		map.put("Cancelado", 2);
		map.put("Concluído", 2);
		return map;
	}
	
	public List<ContractDTO> getContractsByClient(Long client_id){
		List<Contract> contracts = contractRepository.findByClientId(client_id);
		return mapRawResultsToDTOs(contracts);
	}
	
	public List<ContractDTO> getContractsByProfessional(Long professional_id){
		List<Contract> contracts = contractRepository.findByProfessionalId(professional_id);
		return mapRawResultsToDTOs(contracts);
	}
	
	public ContractDTO getContractsById(Long id) {
		Contract contract = contractRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Contract Not Found"));
		List<Contract> list = new ArrayList<>();
		list.add(contract);
		return mapRawResultsToDTOs(list).getFirst();
	}
	
	public Contract createContract(ContractCreateDTO dto) {
		Professional professional = professionalRepository.findById(dto.getProfessional_id())
				.orElseThrow(() -> new EntityNotFoundException("Professional Not Found"));
		com.usp.buildconnect.entity.Service service = serviceRepository.findById(dto.getService_id())
				.orElseThrow(() -> new EntityNotFoundException("Service Not Found"));
		Client client = clientRepository.findById(dto.getClient_id())
				.orElseThrow(() -> new EntityNotFoundException("Client Not Found"));
		Contract contract = new Contract();
		contract.setDescription(dto.getDescription());
		contract.setValue(dto.getValue());
		contract.setDate_init(dto.getDate_init());
		contract.setDate_end(dto.getDate_end());
		contract.setStatus(dto.getStatus());
		contract.setAddress(dto.getAddress());
		contract.setService(service);
		contract.setProfessional(professional);
		contract.setClient(client);
		String message = "Contrato na localização " +
				contract.getAddress() + " entre " + client.getUser().getName() + " e "+
					professional.getUser().getName() + " emitido com sucesso!";
		Contract result =contractRepository.save(contract);
		notificationService.createNotification(message, contract);
		return result;
	}
	
	public Contract updateContract(String status, Long id) {
		Contract contract = contractRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Contract Not Found"));
		if(this.mapStatus.get(status) - mapStatus.get(contract.getStatus()) != 1) throw new IllegalArgumentException("Invalid Status Transaction");
		contract.setStatus(status);
		String message = "Contrato está com status: " + status.toLowerCase();
		notificationService.createNotification(message, contract);
		return contractRepository.save(contract);
	}
	
	private List<ContractDTO> mapRawResultsToDTOs(List<Contract> results){
		List<ContractDTO> list = new ArrayList<>();
		for(Contract contract: results) {
			Long id = contract.getId();
			String description = contract.getDescription();
			double value = contract.getValue();
			String date_init = contract.getDate_init();
			String date_end = contract.getDate_end();
			String status = contract.getStatus();
			String address = contract.getAddress();
			Long service_id = contract.getService().getId();
			Long professional_id = contract.getProfessional().getId();
			Long client_id = contract.getClient().getId();
			ContractDTO dto = new ContractDTO(id, description, value,
					date_init, date_end, status, address, service_id, professional_id, client_id);
			list.add(dto);
		}
		return list;
	}
}
