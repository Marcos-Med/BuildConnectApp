package com.usp.buildconnect.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class IdAvaliation implements Serializable {
	private static final long serialVersionUID = 1L;
	 @Column(name = "fk_profissional_id")
	 private Long professional_id;

	 @Column(name = "fk_cliente_id")
	 private Long client_id;
}
