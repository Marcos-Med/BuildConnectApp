package com.usp.buildconnect.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usp.buildconnect.dto.ProfessionalDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

@Service
public class ProfessionalsService {
	
	@Autowired
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<ProfessionalDTO> search(String query){
		List<String> tokens = new ArrayList<>();
		if(query != null && !query.trim().isEmpty()) {
			tokens = Arrays.stream(query.trim().split("\\s+")).
					filter(s -> !s.isEmpty()).
					collect(Collectors.toList());
		}
		
		if(tokens.isEmpty()) return new ArrayList<>();
		
		StringBuilder sql = new StringBuilder("""
				SELECT
				u.id_usuario AS user_id,
				u.nome AS user_name,
				u.cpf AS user_cpf,
				s.tipo AS service_name,
				p.MEI AS professional_mei,
				p.nota_media AS professional_mean_avaliaton,
				u.cidade AS user_city,
				u.foto AS user_perfil,
				p.capa_perfil AS background_perfil
			FROM Usuario u JOIN Profissional p ON u.id_usuario = p.fk_usuario_id
			LEFT JOIN Prestador_Servicos ps ON ps.fk_profissional_id = p.fk_usuario_id
			LEFT JOIN Servico s ON s.id = ps.fk_servico_id
			WHERE 1=1 
			""");
		
		for(int i = 0; i < tokens.size(); i++) {
			String token = "token" + i;
			sql.append(" AND ("); 
            sql.append(" LOWER(u.nome) LIKE LOWER(CONCAT('%', :").append(token).append(", '%')) ");
            sql.append(" OR LOWER(u.cidade) LIKE LOWER(CONCAT('%', :").append(token).append(", '%')) ");
            sql.append(" OR LOWER(s.tipo) LIKE LOWER(CONCAT('%', :").append(token).append(", '%')) ");
            sql.append(") ");
		}
		
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		
		for(int i = 0; i < tokens.size(); i++) {
			nativeQuery.setParameter("token" + i, tokens.get(i));
		}
		
		return mapRawResultsToDTOs(nativeQuery.getResultList());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ProfessionalDTO> listProfessionalByCity(String city){
		if(city == null || city.trim().isEmpty()) return new ArrayList<>();
		StringBuilder sql = new StringBuilder("""
				SELECT
					u.id_usuario AS user_id,
					u.nome AS user_name,
					u.cpf AS user_cpf,
					s.tipo AS service_name,
					p.MEI AS professional_mei,
					p.nota_media AS professional_mean_avaliaton,
					u.cidade AS user_city,
					u.foto AS user_perfil,
					p.capa_perfil AS background_perfil
				FROM Usuario u JOIN Profissional p ON u.id_usuario = p.fk_usuario_id
				LEFT JOIN Prestador_Servicos ps ON ps.fk_profissional_id = p.fk_usuario_id
				LEFT JOIN Servico s ON s.id = ps.fk_servico_id
				WHERE LOWER(u.cidade) LIKE LOWER(CONCAT('%', :cityParam, '%')) 
				""");
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		nativeQuery.setParameter("cityParam", city);
		return mapRawResultsToDTOs(nativeQuery.getResultList());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ProfessionalDTO> listProfessionalByService(String service){
		if(service == null || service.trim().isEmpty()) return new ArrayList<>();
		StringBuilder sql = new StringBuilder("""
				SELECT
					u.id_usuario AS user_id,
					u.nome AS user_name,
					u.cpf AS user_cpf,
					s.tipo AS service_name,
					p.MEI AS professional_mei,
					p.nota_media AS professional_mean_avaliaton,
					u.cidade AS user_city,
					u.foto AS user_perfil,
					p.capa_perfil AS background_perfil
				FROM Usuario u JOIN Profissional p ON u.id_usuario = p.fk_usuario_id
				LEFT JOIN Prestador_Servicos ps ON ps.fk_profissional_id = p.fk_usuario_id
				LEFT JOIN Servico s ON s.id = ps.fk_servico_id
				WHERE EXISTS(
				 	SELECT 1 FROM Prestador_Servicos p_filter
				 	JOIN Servico s_filter ON s_filter.id = p_filter.fk_servico_id
				 	WHERE p.fk_usuario_id = p_filter.fk_profissional_id AND
				 	LOWER(s.tipo) LIKE LOWER(CONCAT('%', :serviceParam, '%'))
				 )
				"""
				);
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		nativeQuery.setParameter("serviceParam", service);
		return mapRawResultsToDTOs(nativeQuery.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public List<ProfessionalDTO> listProfessionalByName(String name){
		if(name == null || name.trim().isEmpty()) return new ArrayList<>();
		StringBuilder sql = new StringBuilder("""
				SELECT
					u.id_usuario AS user_id,
					u.nome AS user_name,
					u.cpf AS user_cpf,
					s.tipo AS service_name,
					p.MEI AS professional_mei,
					p.nota_media AS professional_mean_avaliaton,
					u.cidade AS user_city,
					u.foto AS user_perfil,
					p.capa_perfil AS background_perfil
				FROM Usuario u JOIN Profissional p ON u.id_usuario = p.fk_usuario_id
				LEFT JOIN Prestador_Servicos ps ON ps.fk_profissional_id = p.fk_usuario_id
				LEFT JOIN Servico s ON s.id = ps.fk_servico_id
				WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nameParam, '%'))
				"""
				);
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		nativeQuery.setParameter("nameParam", name);
		return mapRawResultsToDTOs(nativeQuery.getResultList());
	}
	
	public ProfessionalDTO getProfessionalByID(Long Id){
		StringBuilder sql = new StringBuilder("""
				SELECT
					u.id_usuario AS user_id,
					u.nome AS user_name,
					u.cpf AS user_cpf,
					s.tipo AS service_name,
					p.MEI AS professional_mei,
					p.nota_media AS professional_mean_avaliaton,
					u.cidade AS user_city,
					u.foto AS user_perfil,
					p.capa_perfil AS background_perfil
				FROM Usuario u JOIN Profissional p ON u.id_usuario = p.fk_usuario_id
				LEFT JOIN Prestador_Servicos ps ON ps.fk_profissional_id = p.fk_usuario_id
				LEFT JOIN Servico s ON s.id = ps.fk_servico_id
				WHERE p.fk_usuario_id = :idParam
				"""
				);
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		nativeQuery.setParameter("idParam", Id);
		@SuppressWarnings("unchecked")
		List<ProfessionalDTO> results  = mapRawResultsToDTOs(nativeQuery.getResultList());
		if(results.isEmpty()) throw new EntityNotFoundException("Professional Not Found");
		return results.getFirst();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProfessionalDTO> getProfessionals(){
		StringBuilder sql = new StringBuilder("""
				SELECT
					u.id_usuario AS user_id,
					u.nome AS user_name,
					u.cpf AS user_cpf,
					s.tipo AS service_name,
					p.MEI AS professional_mei,
					p.nota_media AS professional_mean_avaliaton,
					u.cidade AS user_city,
					u.foto AS user_perfil,
					p.capa_perfil AS background_perfil
				FROM Usuario u JOIN Profissional p ON u.id_usuario = p.fk_usuario_id
				LEFT JOIN Prestador_Servicos ps ON ps.fk_profissional_id = p.fk_usuario_id
				LEFT JOIN Servico s ON s.id = ps.fk_servico_id
				"""
				);
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		return mapRawResultsToDTOs(nativeQuery.getResultList());
	}
	
	private List<ProfessionalDTO> mapRawResultsToDTOs(List<Object[]> rawResults) {
        Map<Long, ProfessionalDTO> professionalMap = new HashMap<>();

        for (Object[] row : rawResults) {
            Long id = ((Number) row[0]).longValue();
            String name = (String) row[1]; 
            String cpf = (String) row[2];
            String service = (String) row[3];
            String mei = (String) row[4]; 
            double meanAvaliation = (Double) row[5]; 
            String city = (String) row[6];
            String photo = (String) row[7];
            String background_photo = (String) row[8];

            ProfessionalDTO dto = professionalMap.get(id);

            if (dto == null) {
                List<String> servicesList = new ArrayList<>();
                if (service != null) servicesList.add(service);
                dto = new ProfessionalDTO(id, name, cpf, servicesList, mei, meanAvaliation, city, photo,
                		background_photo);
                professionalMap.put(id, dto);
            } else {
                if (service != null && !dto.getServices().contains(service)) {
                    dto.getServices().add(service);
                }
            }
        }
        return new ArrayList<>(professionalMap.values());
    }
}
