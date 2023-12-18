package org.in.com.service;

import java.util.List;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;

public interface UserService {

	public void updateUser(AuthDTO authDTO, UserDTO userDTO);

	List<UserDTO> getAllUser(AuthDTO authDTO, UserDTO userDto);

	public UserDTO userLogin(UserDTO userDTO);

	public void updateToken(UserDTO userDTO);

	public UserDTO getUser(AuthDTO authDTO, UserDTO userDTO);

	public UserDTO getUserDetails(String authToken);

	public UserDTO getByCode(AuthDTO authDTO, UserDTO userDTO, NamespaceDTO namespaceDTO, String code);

}
