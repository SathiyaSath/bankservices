package org.in.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.springframework.stereotype.Repository;

import lombok.Cleanup;

@Repository
public class NamespaceDAO {

	public void updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws Exception {
		@Cleanup
		Connection connection = null;
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "CALL EZEE_SP_NAMESPACE_IUD(?, ?, ?, ?, ?, ?, NOW())";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, namespaceDTO.getGeneratedString());
			statement.setString(2, namespaceDTO.getName());
			statement.setString(3, namespaceDTO.getAddress());
			statement.setString(4, authDTO.getUserDTO().getAuthtoken());
			statement.setInt(5, namespaceDTO.getActiveFlag());
			statement.setObject(6, authDTO.getUserDTO().getId());
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new RuntimeException("Error executing update namespace to namespace ", e);
		}
	}

	public List<NamespaceDTO> getAllNamespaceDetails(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws Exception {
		List<NamespaceDTO> list = new ArrayList<>();
		@Cleanup
		Connection connection = null;
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "SELECT id, name, address, active_flag, updated_by, updated_at FROM namespace WHERE active_flag < 2";
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			@Cleanup
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				namespaceDTO.setId(resultSet.getInt("id"));
				namespaceDTO.setName(resultSet.getString("name"));
				namespaceDTO.setAddress(resultSet.getString("address"));
				namespaceDTO.setActiveFlag(resultSet.getInt("active_flag"));
				UserDTO user = new UserDTO();
				user.setId(resultSet.getInt("updated_by"));
				namespaceDTO.setUpdatedBy(user);
				list.add(namespaceDTO);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Error executing namespace details from namespace ", e);
		}
		return list;
	}

	public NamespaceDTO getByCode(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws Exception {
		@Cleanup
		Connection connection = null;
		@Cleanup
		PreparedStatement preparedStatement = null;
		@Cleanup
		ResultSet resultSet = null;
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "SELECT id, code, name, address, active_flag, updated_by, updated_at FROM namespace WHERE id = ? AND active_flag = 1";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, namespaceDTO.getId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				namespaceDTO.setId(resultSet.getInt("id"));
				namespaceDTO.setCode(resultSet.getString("code"));
				namespaceDTO.setName(resultSet.getString("name"));
				namespaceDTO.setAddress(resultSet.getString("address"));
				namespaceDTO.setActiveFlag(resultSet.getInt("active_flag"));
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Error executing namespace details from namespace ", e);
		}
		return namespaceDTO;
	}
}
