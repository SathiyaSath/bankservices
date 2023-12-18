package org.in.com.service.impl;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.in.com.ehcache.EhcacheManeger;
import org.in.com.service.AuthService;
import org.in.com.service.CustomerService;
import org.in.com.service.NamespaceService;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.ehcache.Element;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	CustomerService customerService;
	@Autowired
	UserService userService;
	@Autowired
	NamespaceService namespaceService;

	@Override
	public UserDTO userLogin(UserDTO userDTO) {
		UserDTO userDto = new UserDTO();
		UserDTO userDTODB = userService.userLogin(userDTO);
		if (userDTODB.getUsername() != null && userDTODB.getPassword() != null) {
			String token = userDto.GenerateUniqueID();
			userDto.setAuthtoken(token);
			userDto.setUsername(userDTO.getUsername());
			userDto.setPassword(userDTO.getPassword());
			userService.updateToken(userDto);
		}
		return userDto;
	}

	@Override
	public AuthDTO getAuth(String authtoken) {
		Element element = EhcacheManeger.getUserdataCache().get(authtoken);
		AuthDTO authDTO = new AuthDTO();
		UserDTO user = null;
		if (element != null) {
			user = (UserDTO) element.getObjectValue();
		}
		else {
			user = userService.getUserDetails(authtoken);
			element = new Element(authtoken, user);
			EhcacheManeger.getUserdataCache().put(element);
		}
		if (user != null) {
			authDTO.setUserDTO(user);
			NamespaceDTO namespaceDTO = namespaceService.getByCode(authDTO, user.getNamespaceDTO());
			authDTO.setNamespaceDTO(namespaceDTO);
		}
		System.out.println("user data from ehcache" + authDTO);
		return authDTO;
	}

	@Override
	public AuthDTO getAuth(String namespaceCode, String username, String apitoken) {
		Element element = EhcacheManeger.getUserdataCache().get(apitoken);
		AuthDTO authDTO = new AuthDTO();
		UserDTO user = null;
		if (element != null) {
			user = (UserDTO) element.getObjectValue();
		}
		else {
			user = userService.getUserDetails(apitoken);
			element = new Element(apitoken, user);
			EhcacheManeger.getUserdataCache().put(element);
		}
		if (user != null) {
			authDTO.setUserDTO(user);
			NamespaceDTO namespaceDTO = namespaceService.getByCode(authDTO, user.getNamespaceDTO());
			authDTO.setNamespaceDTO(namespaceDTO);
		}
		NamespaceDTO namespaceDTO = authDTO.getNamespaceDTO();
		UserDTO userDTO = authDTO.getUserDTO();
		if (namespaceDTO != null && userDTO != null) {
			if (namespaceDTO.getCode().equals(namespaceCode) && userDTO.getUsername().equals(username) && userDTO.getApitoken().equals(apitoken)) {
				System.out.println(" data verified");
			}
		}
		return authDTO;
	}
}
