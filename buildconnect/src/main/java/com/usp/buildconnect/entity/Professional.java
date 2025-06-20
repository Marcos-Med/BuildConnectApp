package com.usp.buildconnect.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profissional")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professional {
	@Id
	@Column(name="fk_usuario_id")
	private Long id;
	@Column(name = "MEI")
	private String mei;
	@Column(name = "nota_media")
	private double mean_avaliation;
	@Column(name = "capa_perfil")
	private String background_photo;
	
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@MapsId
	@JoinColumn(name = "fk_usuario_id")
	private User user;
	
	@OneToMany(mappedBy="professional", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Avaliation> avaliations = new ArrayList<>();
	
	@OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contract> contract = new ArrayList<>();
}
