package com.usp.buildconnect.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usp.buildconnect.dto.AvaliationCreateDTO;
import com.usp.buildconnect.dto.AvaliationDTO;
import com.usp.buildconnect.dto.AvaliationUpdateDTO;
import com.usp.buildconnect.entity.Avaliation;
import com.usp.buildconnect.entity.Client;
import com.usp.buildconnect.entity.IdAvaliation;
import com.usp.buildconnect.entity.Professional;
import com.usp.buildconnect.repository.AvaliationRepository;
import com.usp.buildconnect.repository.ClientRepository;
import com.usp.buildconnect.repository.ProfessionalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AvaliationsService {
	
	@Autowired
	private AvaliationRepository avaliationRepository;
	@Autowired
	private ProfessionalRepository professionalRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	public List<AvaliationDTO> getAvaliationsByClient(Long client_id){
		List<Avaliation> list = avaliationRepository.findByClientId(client_id);
		return mapRawResultsToDTOs(list);
	}
	
	public List<AvaliationDTO> getAvaliationsByProfessional(Long professional_id){
		List<Avaliation> list = avaliationRepository.findByProfessionalId(professional_id);
		return mapRawResultsToDTOs(list);
	}
	
	public AvaliationDTO getAvaliationById(IdAvaliation id) {
		Avaliation avaliation = avaliationRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Avaliation Not Found"));
		List<Avaliation> list = new ArrayList<>();
		list.add(avaliation);
		return mapRawResultsToDTOs(list).getFirst();
	}
	
	public Avaliation createAvaliation(AvaliationCreateDTO dto) {
		Avaliation avaliation = new Avaliation();
		Professional professional = professionalRepository.findById(dto.getProfessional_id())
				.orElseThrow(()-> new EntityNotFoundException("Professional Not Found"));
		Client client = clientRepository.findById(dto.getClient_id())
				.orElseThrow(()-> new EntityNotFoundException("Client Not Found"));
		IdAvaliation id = new IdAvaliation();
		id.setClient_id(dto.getClient_id());
		id.setProfessional_id(dto.getProfessional_id());
		avaliation.setId(id);
		avaliation.setScore(dto.getScore());
		avaliation.setComment(dto.getComment());
		avaliation.setClient(client);
		avaliation.setProfessional(professional);
		return avaliationRepository.save(avaliation);
	}
	
	public Avaliation updateAvaliation(IdAvaliation id, AvaliationUpdateDTO dto) {
		Avaliation avaliation = avaliationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Avaliation Not Found"));
		avaliation.setScore(dto.getScore());
		avaliation.setComment(dto.getComment());
		return avaliationRepository.save(avaliation);
	}
	
	public void deleteAvaliation(IdAvaliation id) {
		Avaliation avaliation = avaliationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Avaliation Not Found"));
		avaliationRepository.delete(avaliation);
	}
	
	
	
	private List<AvaliationDTO> mapRawResultsToDTOs(List<Avaliation> results){
		List<AvaliationDTO> list = new ArrayList<>();
		for(Avaliation avaliation: results) {
			Long professional_id = avaliation.getId().getProfessional_id();
			Long client_id = avaliation.getId().getClient_id();
			double value = avaliation.getScore();
			String comment = avaliation.getComment();
			list.add(new AvaliationDTO(professional_id, client_id, value, comment));
		}
		return list;
	}
	
}
