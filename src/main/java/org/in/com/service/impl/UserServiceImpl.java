package org.in.com.service.impl;

import java.sql.SQLException;
import java.util.List;
import org.in.com.dao.NamespaceDAO;
import org.in.com.dao.UserDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.in.com.ehcache.EhcacheManeger;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.ehcache.Element;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	NamespaceDAO namespaceDAO;

	@Override
	public void updateUser(AuthDTO authDTO, UserDTO userDTO) {
		try {
			userDAO.updateUser(authDTO, userDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting user details", e);
		}
	}

	@Override
	public List<UserDTO> getAllUser(AuthDTO authDTO, UserDTO userDTO) {
		try {
			return userDAO.getAllUser(authDTO, userDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting  user details.", e);
		}
	}

	@Override
	public UserDTO userLogin(UserDTO userDTO) {
		try {
			userDAO.userLogin(userDTO);
		}
		catch (SQLException e) {
			throw new RuntimeException("An error occurred while getting  user details.", e);
		}
		return userDTO;
	}

	@Override
	public void updateToken(UserDTO userDTO) {
		userDAO.updateToken(userDTO);
	}

	@Override
	public UserDTO getUser(AuthDTO authDTO, UserDTO userDTO) {
		try {
			userDAO.getUser(authDTO, userDTO);
		}
		catch (SQLException e) {
			throw new RuntimeException("An error occurred while getting  user details.", e);
		}
		return userDTO;
	}

	@Override
	public UserDTO getUserDetails(String authToken) {
		UserDTO userDTO = new UserDTO();
		try {
			userDTO = userDAO.getUserDetails(authToken);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting  user details.", e);
		}
		return userDTO;
	}

	@Override
	public UserDTO getByCode(AuthDTO authDTO, UserDTO userDTO, NamespaceDTO namespaceDTO, String code) {
		Element element = EhcacheManeger.getUserCache().get(code);
		UserDTO user = null;
		if (element != null) {
			user = (UserDTO) element.getObjectValue();
		}
		else {
			try {
				namespaceDTO = namespaceDAO.getByCode(authDTO, namespaceDTO);
				user = userDAO.getByCode(authDTO, userDTO, namespaceDTO, code);
			}
			catch (Exception e) {
				throw new RuntimeException("An error occurred while getting  user details.", e);
			}
			element = new Element(code, user);
			EhcacheManeger.getUserCache().put(element);
		}
		System.out.println("user data from ehcache" + user);
		return user;
	}
}
