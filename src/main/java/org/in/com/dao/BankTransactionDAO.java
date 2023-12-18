package org.in.com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.NamespaceDTO;

import org.in.com.dto.UserDTO;
import org.in.com.dto.enumuration.TransactionType;
import org.springframework.stereotype.Repository;

import hirondelle.date4j.DateTime;
import lombok.Cleanup;

@Repository
public class BankTransactionDAO {

	public void updateBankTransaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO, CustomerDTO cutomerDTO) throws Exception {
		@Cleanup
		Connection connection = ConnectionDAO.getConnection();
		try {
			String sql = "CALL EZEE_SP_BANK_TRANSACTION_IUD(? , ?, ?, ? ,? ,? ,? ,? ,? , ?, NOW())";
			@Cleanup
			PreparedStatement satetment = connection.prepareStatement(sql);
			satetment.setString(1, bankTransactionDTO.getGeneratedString());
			satetment.setInt(2, authDTO.getNamespaceDTO().getId());
			satetment.setInt(3, authDTO.getCustomerDTO().getId());
			satetment.setString(4, bankTransactionDTO.getTransactionType().name());
			satetment.setBigDecimal(5, bankTransactionDTO.getTransactionAmount());
			satetment.setBigDecimal(6, bankTransactionDTO.getCreditAmount());
			satetment.setBigDecimal(7, bankTransactionDTO.getDebitAmount());
			satetment.setString(8, bankTransactionDTO.getMobileNumber());
			satetment.setInt(9, bankTransactionDTO.getActiveFlag());
			satetment.setInt(10, authDTO.getUserDTO().getId());
			satetment.executeUpdate();
		}
		catch (SQLException e) {
			throw new RuntimeException("updateBankTransaction failed for transaction", e);
		}
	}

	public List<BankTransactionDTO> getAllTransaction(AuthDTO authDTO, UserDTO userDTO, NamespaceDTO namespaceDTO, CustomerDTO customerDTO) throws Exception {
		@Cleanup
		Connection connection = null;
		List<BankTransactionDTO> list = new ArrayList<>();
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, transaction_type, transaction_amount, credit_amount, debit_amount, active_flag, updated_by, updated_at FROM bank_transaction WHERE active_flag < 2 AND namespace_id = ?";
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getNamespaceDTO().getId());
			@Cleanup
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
				bankTransactionDTO.setCode(resultSet.getString("code"));
				String transactionTypeString = resultSet.getString("transaction_type");
				TransactionType transactionType = TransactionType.valueOf(transactionTypeString);
				bankTransactionDTO.setTransactionType(transactionType);
				bankTransactionDTO.setTransactionAmount(resultSet.getBigDecimal("transaction_amount"));
				bankTransactionDTO.setCreditAmount(resultSet.getBigDecimal("credit_amount"));
				bankTransactionDTO.setDebitAmount(resultSet.getBigDecimal("debit_amount"));
				bankTransactionDTO.setActiveFlag(resultSet.getInt("active_flag"));
				list.add(bankTransactionDTO);
			}
		}
		catch (SQLException e) {
			throw new RuntimeException("Error executing Get DEtails in transaction", e);
		}
		return list;
	}

	public BankTransactionDTO getTransactionByCode(AuthDTO authDTO, NamespaceDTO namespaceDTO, CustomerDTO customerDTO, String code) throws Exception {
		@Cleanup
		Connection connection = null;
		BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, transaction_type, transaction_amount, credit_amount, debit_amount, active_flag, updated_by, updated_at FROM bank_transaction WHERE active_flag = 1 AND code = ? AND namespace_id = ?";
			@Cleanup
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			preparedStatement.setInt(2, authDTO.getNamespaceDTO().getId());
			@Cleanup
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				bankTransactionDTO.setCode(resultSet.getString("code"));
				String transactionTypeString = resultSet.getString("transaction_type");
				TransactionType transactionType = TransactionType.valueOf(transactionTypeString);
				bankTransactionDTO.setTransactionType(transactionType);
				bankTransactionDTO.setTransactionAmount(resultSet.getBigDecimal("transaction_amount"));
				bankTransactionDTO.setCreditAmount(resultSet.getBigDecimal("credit_amount"));
				bankTransactionDTO.setDebitAmount(resultSet.getBigDecimal("debit_amount"));
				bankTransactionDTO.setActiveFlag(resultSet.getInt("active_flag"));
				UserDTO user = new UserDTO();
				user.setId(resultSet.getInt("updated_by"));
				bankTransactionDTO.setUpdatedBy(user);
			}
		}
		catch (SQLException e) {
			throw new RuntimeException("Error executing Get DEtails in transaction", e);
		}
		return bankTransactionDTO;
	}

	public List<BankTransactionDTO> executeFromToDateProcedure(AuthDTO authDTO, String code, String fromDate, String toDate) throws Exception {
		@Cleanup
		Connection connection = null;
		List<BankTransactionDTO> list = new ArrayList<>();
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "CALL FROMEDATETODATE(?, ?, ?, ?)";
			@Cleanup
			CallableStatement statement = connection.prepareCall(sql);
			DateTime dateAndTime = new DateTime(fromDate);
			dateAndTime.format("YYYY-MM-DD");
			DateTime dateAndTimes = new DateTime(toDate);
			dateAndTimes.format("YYYY-MM-DD");
			statement.setInt(1, authDTO.getNamespaceDTO().getId());
			statement.setInt(2, authDTO.getCustomerDTO().getId());
			statement.setTimestamp(3, new Timestamp(dateAndTime.getMilliseconds(TimeZone.getDefault())));
			statement.setTimestamp(4, new Timestamp(dateAndTimes.getMilliseconds(TimeZone.getDefault())));
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				BankTransactionDTO bankTransactionDto = new BankTransactionDTO();
				NamespaceDTO namespaceDTO = new NamespaceDTO();
				CustomerDTO customerDto = new CustomerDTO();
				String transaction = resultSet.getString("namespace_id");
				namespaceDTO.setCode(transaction);
				bankTransactionDto.setNamespaceDTO(namespaceDTO);
				String transactions = resultSet.getString("customer_id");
				customerDto.setCode(transactions);
				bankTransactionDto.setCustomerDTO(customerDto);
				String transactionTypeString = resultSet.getString("transaction_type");
				TransactionType transactionType = TransactionType.valueOf(transactionTypeString);
				bankTransactionDto.setTransactionType(transactionType);
				bankTransactionDto.setTransactionAmount(resultSet.getBigDecimal("transaction_amount"));
				bankTransactionDto.setCreditAmount(resultSet.getBigDecimal("credit_amount"));
				bankTransactionDto.setDebitAmount(resultSet.getBigDecimal("debit_amount"));
				bankTransactionDto.setActiveFlag(resultSet.getInt("active_flag"));
				list.add(bankTransactionDto);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Error executing transaction history", e);
		}
		return list;
	}
}
