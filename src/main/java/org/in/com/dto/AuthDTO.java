package org.in.com.dto;

import lombok.Data;

@Data
public class AuthDTO {
	public String authtoken;
	private UserDTO userDTO;
	private NamespaceDTO namespaceDTO;
	private CustomerDTO customerDTO;
}
