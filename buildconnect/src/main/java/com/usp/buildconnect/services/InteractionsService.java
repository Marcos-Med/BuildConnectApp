package com.usp.buildconnect.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usp.buildconnect.dto.InteractionCreateDTO;
import com.usp.buildconnect.dto.InteractionDTO;
import com.usp.buildconnect.dto.InteractionUpdateDTO;
import com.usp.buildconnect.entity.Client;
import com.usp.buildconnect.entity.IdInteraction;
import com.usp.buildconnect.entity.Interaction;
import com.usp.buildconnect.entity.Post;
import com.usp.buildconnect.repository.ClientRepository;
import com.usp.buildconnect.repository.InteractionRepository;
import com.usp.buildconnect.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class InteractionsService {
	@Autowired
	private InteractionRepository interactionRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private PostRepository postRepository;
	
	public List<InteractionDTO> getInteractionsByClient(Long client_id){
		List<Interaction> list = interactionRepository.findByClientId(client_id);
		return mapRawResultsToDTOs(list);
	}
	
	public List<InteractionDTO> getInteractionsByPost(Long post_id){
		List<Interaction> list = interactionRepository.findByPostId(post_id);
		return mapRawResultsToDTOs(list);
	}
	
	public InteractionDTO getInteractionById(IdInteraction id) {
		Interaction interaction = interactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Interaction Not Found"));
		List<Interaction> list = new ArrayList<>();
		list.add(interaction);
		return mapRawResultsToDTOs(list).getFirst();
	}
	
	public Interaction createInteraction(InteractionCreateDTO dto) {
		Client client = clientRepository.findById(dto.getClient_id())
				.orElseThrow(() -> new EntityNotFoundException("Client Not Found"));
		Post post = postRepository.findById(dto.getPost_id())
				.orElseThrow(() -> new EntityNotFoundException("Post Not Found"));
		Interaction i = new Interaction();
		i.setClient(client);
		i.setPost(post);
		IdInteraction id = new IdInteraction();
		id.setClient_id(dto.getClient_id());
		id.setPost_id(dto.getPost_id());
		i.setId(id);
		i.setScore(dto.getScore());
		i.setComment(dto.getComment());
		return interactionRepository.save(i);
	}
	
	public Interaction updateInteraction(IdInteraction id, InteractionUpdateDTO dto) {
		Interaction i = interactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Interaction Not Found"));
		i.setComment(dto.getComment());
		i.setScore(dto.getScore());
		return interactionRepository.save(i);
	}
	
	public void deleteInteraction(IdInteraction id) {
		Interaction i = interactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Interaction Not Found"));
		interactionRepository.delete(i);
	}
	
	private List<InteractionDTO> mapRawResultsToDTOs(List<Interaction> results){
		List<InteractionDTO> list = new ArrayList<>();
		for(Interaction i: results) {
			Long client_id = i.getId().getClient_id();
			Long post_id = i.getId().getPost_id();
			double score = i.getScore();
			String comment = i.getComment();
			list.add(new InteractionDTO(client_id, post_id, score, comment));
		}
		return list;
	}
}
