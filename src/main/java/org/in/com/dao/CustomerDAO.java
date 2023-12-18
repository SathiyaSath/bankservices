package org.in.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.springframework.stereotype.Repository;

import lombok.Cleanup;

@Repository
public class CustomerDAO {

	public void updateCustomer(AuthDTO authDTO, CustomerDTO customerDTO) throws Exception {
		@Cleanup
		Connection connection = null;
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "CALL EZEE_SP_CUSTOMER_IUD(?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?, ?, ?, NOW())";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(sql);
			int index = 0;
			statement.setString(++index, customerDTO.getGeneratedString());
			statement.setInt(++index, authDTO.getNamespaceDTO().getId());
			statement.setInt(++index, authDTO.getUserDTO().getId());
			statement.setString(++index, customerDTO.getFirstName());
			statement.setString(++index, customerDTO.getLastName());
			statement.setString(++index, customerDTO.getAddress());
			statement.setString(++index, customerDTO.getMobileNumber());
			statement.setString(++index, customerDTO.getEmailId());
			statement.setString(++index, customerDTO.getAadharNo());
			statement.setString(++index, customerDTO.getPancardNo());
			statement.setString(++index, customerDTO.getAccountNo());
			statement.setString(++index, customerDTO.getAccountType());
			statement.setString(++index, customerDTO.getBranchName());
			statement.setString(++index, customerDTO.getIfscCode());
			statement.setString(++index, customerDTO.getUsername());
			statement.setString(++index, customerDTO.getPassword());
			statement.setInt(++index, customerDTO.getActiveFlag());
			statement.setObject(++index, authDTO.getUserDTO().getId());
			statement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("updateCustomer failed for transaction", e);
		}
	}

	public List<CustomerDTO> getAllCustomer(AuthDTO authDTO, CustomerDTO customerDTO) throws Exception {
		List<CustomerDTO> list = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT namespace_id ,first_name, last_name, address, mobile_number, email_id, aadhar_no, pancard_no, account_no, account_type, branch_name, ifsc_code, active_flag, updated_by, updated_at FROM customer WHERE active_flag < 2 AND namespace_id = ? ";
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getNamespaceDTO().getId());
			@Cleanup
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				CustomerDTO customerDto = new CustomerDTO();
				NamespaceDTO namespaceDTO = new NamespaceDTO();
				String transaction = resultSet.getString("namespace_id");
				namespaceDTO.setCode(transaction);
				customerDto.setNamespaceDTO(namespaceDTO);
				customerDto.setFirstName(resultSet.getString("first_name"));
				customerDto.setLastName(resultSet.getString("last_name"));
				customerDto.setAddress(resultSet.getString("address"));
				customerDto.setMobileNumber(resultSet.getString("mobile_number"));
				customerDto.setEmailId(resultSet.getString("email_id"));
				customerDto.setAadharNo(resultSet.getString("aadhar_no"));
				customerDto.setPancardNo(resultSet.getString("pancard_no"));
				customerDto.setAccountNo(resultSet.getString("account_no"));
				customerDto.setAccountType(resultSet.getString("account_type"));
				customerDto.setBranchName(resultSet.getString("branch_name"));
				customerDto.setIfscCode(resultSet.getString("ifsc_code"));
				customerDto.setActiveFlag(resultSet.getInt("active_flag"));
				UserDTO user = new UserDTO();
				user.setId(resultSet.getInt("updated_by"));
				customerDto.setUpdatedBy(user);
				list.add(customerDto);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error executing get details from customer ", e);

		}
		return list;
	}

	public CustomerDTO getCustomerDetails(AuthDTO authDTO, CustomerDTO customerDTO, String code) throws Exception {
		@Cleanup
		Connection connection = null;
		@Cleanup
		PreparedStatement preparedStatement = null;
		@Cleanup
		ResultSet resultSet = null;
		CustomerDTO customerDto = new CustomerDTO();
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "SELECT id, code, namespace_id, first_name, last_name, address, mobile_number, email_id, aadhar_no, pancard_no, account_no, account_type, branch_name, username, password, ifsc_code, active_flag, updated_by, updated_at FROM customer WHERE active_flag = 1 AND code = ? AND namespace_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			preparedStatement.setInt(2, authDTO.getNamespaceDTO().getId());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				NamespaceDTO namespaceDTO = new NamespaceDTO();
				customerDto.setId(resultSet.getInt("id"));
				customerDto.setCode(resultSet.getString("code"));
				String transaction = resultSet.getString("namespace_id");
				namespaceDTO.setCode(transaction);
				customerDto.setNamespaceDTO(namespaceDTO);
				customerDto.setFirstName(resultSet.getString("first_name"));
				customerDto.setLastName(resultSet.getString("last_name"));
				customerDto.setAddress(resultSet.getString("address"));
				customerDto.setMobileNumber(resultSet.getString("mobile_number"));
				customerDto.setEmailId(resultSet.getString("email_id"));
				customerDto.setAadharNo(resultSet.getString("aadhar_no"));
				customerDto.setPancardNo(resultSet.getString("pancard_no"));
				customerDto.setAccountNo(resultSet.getString("account_no"));
				customerDto.setAccountType(resultSet.getString("account_type"));
				customerDto.setBranchName(resultSet.getString("branch_name"));
				customerDto.setIfscCode(resultSet.getString("ifsc_code"));
				customerDto.setUsername(resultSet.getString("username"));
				customerDto.setPassword(resultSet.getString("password"));
				customerDto.setActiveFlag(resultSet.getInt("active_flag"));
				UserDTO user = new UserDTO();
				user.setId(resultSet.getInt("updated_by"));
				customerDto.setUpdatedBy(user);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error executing get details from customer ", e);
		}
		return customerDto;
	}

	public CustomerDTO getCustomerByUser(AuthDTO authDTO, UserDTO user, CustomerDTO customerDTO) throws SQLException {
		@Cleanup
		Connection connection = null;
		try {
			connection = ConnectionDAO.getConnection();
			String str = "SELECT id, code ,namespace_id, user_id, username, password FROM customer WHERE user_id = ? AND code = ? AND active_flag = 1";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(str);
			statement.setInt(1, authDTO.getUserDTO().getId());
			statement.setString(2, customerDTO.getCode());
			@Cleanup
			ResultSet resultset = statement.executeQuery();
			if (resultset.next()) {
				NamespaceDTO namespaceDTO = new NamespaceDTO();
				customerDTO.setId(resultset.getInt("id"));
				int transaction = resultset.getInt("namespace_id");
				namespaceDTO.setId(transaction);
				customerDTO.setNamespaceDTO(namespaceDTO);
				customerDTO.setUserId(resultset.getInt("user_id"));
				customerDTO.setUsername(resultset.getString("username"));
				customerDTO.setPassword(resultset.getString("password"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error executing get details from customer ", e);
		}
		System.out.println(customerDTO);
		return customerDTO;
	}
}
