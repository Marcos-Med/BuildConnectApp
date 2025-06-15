package com.usp.buildconnect.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="publicacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="titulo")
	private String title;
	@Column(name="descricao")
	private String description;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Image> images = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="fk_profissional_id")
	private Professional professional;

}
