package com.usp.buildconnect.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usp.buildconnect.dto.PostCreateDTO;
import com.usp.buildconnect.dto.PostDTO;
import com.usp.buildconnect.dto.PostUpdateDTO;
import com.usp.buildconnect.entity.Image;
import com.usp.buildconnect.entity.Post;
import com.usp.buildconnect.entity.Professional;
import com.usp.buildconnect.repository.PostRepository;
import com.usp.buildconnect.repository.ProfessionalRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

@Service
public class PostsService {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ProfessionalRepository professionalRepository;
	
	public Post createPost(PostCreateDTO dto) {
		Professional professional = professionalRepository.findById(dto.getProfessional_id()).
				orElseThrow(()-> new EntityNotFoundException("Professional Not Found"));
		Post post = new Post();
		post.setTitle(dto.getTitle());
		post.setDescription(dto.getDescription());
		post.setProfessional(professional);
		
		List<Image> images = dto.getUrl_images() != null ?
				dto.getUrl_images().stream()
				.map(url ->{
					Image img = new Image();
					img.setUrl(url);
					img.setPost(post);
					return img;
				}).collect(Collectors.toList()):
					new ArrayList<>();
		post.setImages(images);
		return postRepository.save(post);
	}
	
	public Post updatePost(Long id, PostUpdateDTO dto) {
		Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Post Not Found"));
		post.setTitle(dto.getTitle());
		post.setDescription(dto.getDescription());
		
		List<Image> images = dto.getUrl_images() != null ?
				dto.getUrl_images().stream().map(
						url -> {
							Image img = new Image();
							img.setUrl(url);
							img.setPost(post);
							return img;
						}).collect(Collectors.toList()):
							new ArrayList<>();
		post.getImages().clear();
		post.getImages().addAll(images);
		return postRepository.save(post);
	}
	
	public void deletePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Post Not Found"));
		postRepository.delete(post);
	}
	
	@SuppressWarnings("unchecked")
	public PostDTO getPost(Long ID){
		StringBuilder sql = new StringBuilder("""
				SELECT id, titulo AS title, descricao AS descriptions, caminho_imagem AS url_image
				FROM publicacao JOIN profissional ON fk_usuario_id = fk_profissional_id
				LEFT JOIN imagens ON id = fk_publicacao_id
				WHERE id = :idParam
				""");
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		nativeQuery.setParameter("idParam", ID);
		List<PostDTO> list = mapsRawResultsToDTOs(nativeQuery.getResultList());
		if(list.isEmpty()) throw new EntityNotFoundException("Post Not Found");
		return list.getFirst();
	}
	
	@SuppressWarnings("unchecked")
	public List<PostDTO> getListPostsByProfessional(Long professional_id){
		StringBuilder sql = new StringBuilder("""
				SELECT id, titulo AS title, descricao AS descriptions, caminho_imagem AS url_image
				FROM publicacao JOIN profissional ON fk_usuario_id = fk_profissional_id
				LEFT JOIN imagens ON id = fk_publicacao_id
				WHERE fk_profissional_id = :idProfessionalParam
				""");
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		nativeQuery.setParameter("idProfessionalParam", professional_id);
		return mapsRawResultsToDTOs(nativeQuery.getResultList());
	}
	
	
	
	private List<PostDTO> mapsRawResultsToDTOs(List<Object[]> rawResults){
		Map<Long, PostDTO> postsMap = new HashMap<>();
		for(Object[] result: rawResults) {
			Long id = ((Number) result[0]).longValue();
			String title = (String) result[1];
			String description = (String) result[2];
			String url_image = (String) result[3];
			
			PostDTO dto = postsMap.get(id);
			if(dto == null) {
				List<String> urls_image = new ArrayList<>();
				if(url_image != null) urls_image.add(url_image);
				dto = new PostDTO(id, title, description, urls_image);
				postsMap.put(id, dto);
			} else {
				if(url_image != null && !dto.getUrls_image().contains(url_image)) {
					dto.getUrls_image().add(url_image);
				}
			}
		}
		return new ArrayList<>(postsMap.values());
	}
}
