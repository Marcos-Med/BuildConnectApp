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
import com.usp.buildconnect.dto.PostCreateDTO;
import com.usp.buildconnect.dto.PostDTO;
import com.usp.buildconnect.dto.PostUpdateDTO;
import com.usp.buildconnect.entity.Post;
import com.usp.buildconnect.services.PostsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Posts")
public class PostsController {
	
	@Autowired
	private PostsService postsService;
	
	@GetMapping("/ByProfessional")
	public ResponseEntity<?> getListPostsByProfessional(@RequestParam("id_professional") Long id){
		List<PostDTO> list = postsService.getListPostsByProfessional(id);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
	
	@GetMapping("/ByID")
	public ResponseEntity<?> getPost(@RequestParam("id") Long id){
		PostDTO dto = postsService.getPost(id);
		return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody @Valid PostCreateDTO dto){
		Post post = postsService.createPost(dto);
		Long id = post.getId();
		return ResponseEntity.created(URI.create("/posts/" +  id))
				.build();
	}
	
	@PutMapping("/ByID")
	public ResponseEntity<Void> updatePost(@RequestParam("post_id") Long id, @RequestBody @Valid PostUpdateDTO dto){
		postsService.updatePost(id, dto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/ByID")
	public ResponseEntity<Void> deletePost(@RequestParam("post_id") Long id){
		postsService.deletePost(id);
		return ResponseEntity.noContent().build();
	}
	
}
