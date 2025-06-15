package com.usp.buildconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="avalia")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Avaliation {
	@EmbeddedId
	private IdAvaliation id;
	@Column(name="nota")
	private double score;
	@Column(name="comentario")
	private String comment;
	
	
	@ManyToOne
	@MapsId("professional_id")
	@JoinColumn(name="fk_profissional_id")
	private Professional professional;
	
	@ManyToOne
	@MapsId("client_id")
	@JoinColumn(name="fk_cliente_id")
	private Client client;
}
