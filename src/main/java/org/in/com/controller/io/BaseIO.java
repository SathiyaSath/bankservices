package org.in.com.controller.io;

import lombok.Data;

@Data
public class BaseIO {
	private String code;
	private String name;
	private String authtoken;
	private int activeFlag;
	private UserIO updatedBy;
	private String updatedAt;
	private String message;
}
