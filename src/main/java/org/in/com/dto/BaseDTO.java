package org.in.com.dto;

import hirondelle.date4j.DateTime;
import lombok.Data;

@Data
public class BaseDTO {
	private int id;
	private String code;
	private int activeFlag;
	private UserDTO updatedBy;
	protected DateTime updatedAt;
}
