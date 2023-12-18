package org.in.com.controller;

import org.in.com.controller.io.UserIO;
import org.in.com.dto.UserDTO;
import org.in.com.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UserIO userLogin(@RequestBody UserIO userIO) throws Exception {
		UserIO user = new UserIO();
		try {
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(userIO.getUsername());
			userDTO.setPassword(userIO.getPassword());
			UserDTO userDto = authService.userLogin(userDTO);
			user.setAuthtoken(userDto.getAuthtoken());
			user.setUsername(userDto.getUsername());
			user.setPassword(userDto.getPassword());
			if (user.getAuthtoken() == null) {
				throw new Exception("Invalid username and password");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
