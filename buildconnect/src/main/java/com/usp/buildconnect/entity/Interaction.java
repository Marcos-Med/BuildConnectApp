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
@Table(name="interage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interaction {
	@EmbeddedId
	private IdInteraction id;
	@Column(name="comentario")
	private String comment;
	@Column(name="nota")
	private double score;
	
	@ManyToOne
	@MapsId("client_id")
	@JoinColumn(name="fk_cliente_id")
	private Client client;
	
	@ManyToOne
	@MapsId("post_id")
	@JoinColumn(name="fk_publicacao_id")
	private Post post;
}
