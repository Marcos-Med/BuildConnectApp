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
@Table(name="contrato")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contract {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="descricao")
	private String description;
	@Column(name="valor")
	private double value;
	@Column(name="data_inicio")
	private String date_init;
	@Column(name="data_entrega")
	private String date_end;
	private String status;
	@Column(name="endereco")
	private String address;
	
	@ManyToOne
	@JoinColumn(name="fk_servico_id")
	private Service service;
	
	@ManyToOne
	@JoinColumn(name="fk_profissional_id")
	private Professional professional;
	
	@ManyToOne
	@JoinColumn(name="fk_cliente_id")
	private Client client;
	
	@OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Notification> interactions = new ArrayList<>();
}
