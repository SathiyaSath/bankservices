package org.in.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.springframework.stereotype.Repository;

import lombok.Cleanup;

@Repository
public class UserDAO {

	public void updateUser(AuthDTO authDTO, UserDTO userDTO) throws Exception {
		@Cleanup
		Connection connection = null;
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "CAll  EZEE_SP_USER_IUD (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
			try (PreparedStatement statement = connection.prepareCall(sql)) {
				statement.setString(1, userDTO.getGeneratedString());
				statement.setInt(2, authDTO.getNamespaceDTO().getId());
				statement.setString(3, userDTO.getFirstName());
				statement.setString(4, userDTO.getLastName());
				statement.setString(5, userDTO.getAddress());
				statement.setString(6, userDTO.getUserRole());
				statement.setString(7, userDTO.getUsername());
				statement.setString(8, userDTO.getPassword());
				statement.setString(9, userDTO.getAuthtoken());
				statement.setInt(10, userDTO.getActiveFlag());
				statement.setInt(11, authDTO.getUserDTO().getId());
				statement.executeUpdate();
			}
		}
		catch (SQLException e) {
			throw new RuntimeException("error occure update details to user", e);
		}
	}

	public List<UserDTO> getAllUser(AuthDTO authDTO, UserDTO userDTO) throws Exception {
		@Cleanup
		Connection connection = null;
		List<UserDTO> list = new ArrayList<>();
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "SELECT first_name, last_name, user_role, address, active_flag, updated_by, updated_at FROM user WHERE active_flag < 2 AND namespace_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, authDTO.getNamespaceDTO().getId());
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						UserDTO userDto = new UserDTO();
						userDto.setFirstName(resultSet.getString("first_name"));
						userDto.setLastName(resultSet.getString("last_name"));
						userDto.setUserRole(resultSet.getString("user_role"));
						userDto.setAddress(resultSet.getString("address"));
						userDto.setActiveFlag(resultSet.getInt("active_flag"));
						list.add(userDto);
					}
				}
			}
		}
		catch (SQLException e) {
			throw new RuntimeException("error occure get details from user", e);
		}
		return list;
	}

	public UserDTO userLogin(UserDTO userDTO) throws SQLException {
		@Cleanup
		Connection connection = null;
		UserDTO userdto = new UserDTO();
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "SELECT username, password FROM user WHERE username = ? AND password= ? ";
			@Cleanup
			PreparedStatement statement = connection.prepareCall(sql);
			statement.setString(1, userDTO.getUsername());
			statement.setString(2, userDTO.getPassword());
			@Cleanup
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				userdto.setUsername(resultSet.getString("username"));
				userdto.setPassword(resultSet.getString("password"));
			}
		}
		catch (Exception e) {
			throw new RuntimeException("error occure login for user", e);
		}
		return userdto;
	}

	public void updateToken(UserDTO userDTO) {
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "UPDATE user SET auth_token = ? WHERE username= ? and password= ?";
			@Cleanup
			PreparedStatement statement = connection.prepareCall(sql);
			statement.setString(1, userDTO.getAuthtoken());
			statement.setString(2, userDTO.getUsername());
			statement.setString(3, userDTO.getPassword());
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new RuntimeException("error occure when update the token", e);
		}
	}

	public UserDTO getUser(AuthDTO authDTO, UserDTO userDTO) throws SQLException {
		@Cleanup
		Connection connection = null;
		try {
			connection = ConnectionDAO.getConnection();
			String str = "SELECT code, username, password, first_Name, last_Name, user_Role, address, auth_token, api_token, active_flag, updated_By FROM user WHERE active_flag = 1 AND code = ?";
			@Cleanup
			Statement statement = connection.createStatement();
			@Cleanup
			ResultSet resultSet = statement.executeQuery(str);
			if (resultSet.next()) {
				UserDTO userDto = new UserDTO();
				userDto.setCode(resultSet.getString("code"));
				userDto.setUsername(resultSet.getString("username"));
				userDto.setPassword(resultSet.getString("password"));
				userDto.setFirstName(resultSet.getString("first_Name"));
				userDto.setLastName(resultSet.getString("last_Name"));
				userDto.setUserRole(resultSet.getString("user_Role"));
				userDto.setAddress(resultSet.getString("address"));
				userDto.setAuthtoken(resultSet.getString("auth_token"));
				userDto.setApitoken(resultSet.getString("api_token"));
				userDto.setActiveFlag(resultSet.getInt("active_flag"));
			}
		}
		catch (Exception e) {
			throw new RuntimeException("error occure get details from user", e);
		}
		return userDTO;
	}

	public UserDTO getUserDetails(String authtoken) throws Exception {
		UserDTO userDTO = new UserDTO();
		@Cleanup
		Connection connection = null;
		connection = ConnectionDAO.getConnection();
		String str = "SELECT id, namespace_id, username, password, api_token FROM user WHERE auth_token = ?";
		@Cleanup
		PreparedStatement statement = connection.prepareStatement(str);
		statement.setString(1, authtoken);
		@Cleanup
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			NamespaceDTO namespaceDTO = new NamespaceDTO();
			userDTO.setId(resultSet.getInt("id"));
			int transaction = resultSet.getInt("namespace_id");
			namespaceDTO.setId(transaction);
			userDTO.setNamespaceDTO(namespaceDTO);
			userDTO.setUsername(resultSet.getString("username"));
			userDTO.setPassword(resultSet.getString("password"));
			userDTO.setApitoken(resultSet.getString("api_token"));
		}
		return userDTO;
	}

	public UserDTO getByCode(AuthDTO authDTO, UserDTO userDTO, NamespaceDTO namespaceDTO, String code) throws Exception {
		UserDTO userDto = new UserDTO();
		String sql = "SELECT code, namespace_id, username, password, first_name, last_name, user_role, address, auth_token, active_flag, updated_by, updated_at FROM user WHERE active_flag < 2 AND code = ? AND namespace_id = ?";
		@Cleanup
		Connection connection = ConnectionDAO.getConnection();
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, code);
		preparedStatement.setInt(2, authDTO.getUserDTO().getNamespaceDTO().getId());
		@Cleanup
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			userDto.setCode(resultSet.getString("code"));
			int transaction = resultSet.getInt("namespace_id");
			namespaceDTO.setId(transaction);
			userDto.setNamespaceDTO(namespaceDTO);
			userDto.setUsername(resultSet.getString("username"));
			userDto.setPassword(resultSet.getString("password"));
			userDto.setFirstName(resultSet.getString("first_name"));
			userDto.setLastName(resultSet.getString("last_name"));
			userDto.setUserRole(resultSet.getString("user_role"));
			userDto.setAddress(resultSet.getString("address"));
			userDto.setAuthtoken(resultSet.getString("auth_token"));
			userDto.setActiveFlag(resultSet.getInt("active_flag"));
			UserDTO user = new UserDTO();
			user.setId(resultSet.getInt("updated_by"));
			userDto.setUpdatedBy(user);
		}
		else {
			throw new Exception("User with code " + code + " not found");
		}
		return userDto;
	}

}
