package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.in.com.controller.io.UserIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.in.com.service.AuthService;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{authtoken}/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UserIO updateUser(@PathVariable("authtoken") String authtoken, @RequestBody UserIO userIO) throws Exception {
		UserIO userio = new UserIO();
		UserDTO userDto = new UserDTO();
		AuthDTO auth = authService.getAuth(authtoken);
		auth.setNamespaceDTO(auth.getNamespaceDTO());
		userDto.setFirstName(userIO.getFirstName());
		userDto.setLastName(userIO.getLastName());
		userDto.setAddress(userIO.getAddress());
		userDto.setUserRole(userIO.getUserRole());
		userDto.setUsername(userIO.getUsername());
		userDto.setPassword(userIO.getPassword());
		userDto.setActiveFlag(userIO.getActiveFlag());
		userDto.setAuthtoken(userIO.getAuthtoken());
		userService.updateUser(auth, userDto);
		userio.setActiveFlag(userDto.getActiveFlag());
		userio.setCode(userDto.getCode());
		return userio;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UserIO> getAllUserDetails(@PathVariable("authtoken") String authtoken) throws Exception {
		List<UserIO> user = new ArrayList<UserIO>();
		AuthDTO auth = authService.getAuth(authtoken);
		UserDTO uerDTO = new UserDTO();
		List<UserDTO> userDtolist = userService.getAllUser(auth, uerDTO);
		for (UserDTO userDTO : userDtolist) {
			UserIO userIO = new UserIO();
			userIO.setFirstName(userDTO.getFirstName());
			userIO.setLastName(userDTO.getLastName());
			userIO.setUserRole(userDTO.getUserRole());
			userIO.setAddress(userDTO.getAddress());
			userIO.setActiveFlag(userDTO.getActiveFlag());
			user.add(userIO);
		}
		return user;
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UserIO getByCode(@PathVariable("authtoken") String authtoken, @PathVariable String code) throws Exception {
		UserDTO uerDTO = new UserDTO();
		NamespaceDTO namespaceDto = new NamespaceDTO();
		AuthDTO auth = authService.getAuth(authtoken);
		UserDTO users = userService.getByCode(auth, uerDTO, namespaceDto, code);
		UserIO userIO = new UserIO();
		userIO.setFirstName(users.getFirstName());
		userIO.setLastName(users.getLastName());
		userIO.setUserRole(users.getUserRole());
		userIO.setAddress(users.getAddress());
		userIO.setActiveFlag(users.getActiveFlag());
		userIO.setUsername(users.getUsername());
		userIO.setPassword(users.getPassword());
		return userIO;
	}
}
