package org.in.com.service;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.UserDTO;

public interface AuthService {

	public UserDTO userLogin(UserDTO userDTO);

	public AuthDTO getAuth(String authtoken);

	public AuthDTO getAuth(String namespaceCode, String username, String apiToken);

}
