package com.usp.buildconnect.entity;

import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuario") //indica a tabela a ser consultada
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long id;
	@Column(name = "e_mail")
	private String username;
	@Column(name = "senha")
    private String password;
	@Column(name= "nome")
	private String name;
	@Column(name = "cidade")
	private String city;
	@Column(name = "CPF")
	private String cpf;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval= true)
    @PrimaryKeyJoinColumn
    private Professional professional;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval= true)
    @PrimaryKeyJoinColumn
    private Client client;
    
    public String getRole() {
    	return client == null ? "PROFESSIONAL" : "CLIENT";
    }
}
