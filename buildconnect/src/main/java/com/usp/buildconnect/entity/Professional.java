package com.usp.buildconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Profissional")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professional {
	@Id
	private Long id;
	@Column(name = "MEI")
	private String mei;
	@Column(name = "nota_media")
	private double mean_avaliation;
	
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private User user;
}
