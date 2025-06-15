package com.usp.buildconnect.entity;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class IdAvaliation implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long professional_id;
	private Long client_id;
	
}
