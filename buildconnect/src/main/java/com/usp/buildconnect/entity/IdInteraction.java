package com.usp.buildconnect.entity;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class IdInteraction implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long client_id;
	private Long post_id;

}
