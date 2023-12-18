package org.in.com.service.impl;

import java.util.List;
import org.in.com.dao.BankTransactionDAO;
import org.in.com.dao.CustomerDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.in.com.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

	@Autowired
	BankTransactionDAO bankTransactionDAO;
	@Autowired
	CustomerDAO customerDAO;

	@Override
	public void updateBankTransaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO, CustomerDTO customerDTO) {
		UserDTO user = new UserDTO();
		try {
			customerDTO = customerDAO.getCustomerByUser(authDTO, user, customerDTO);
			authDTO.setCustomerDTO(customerDTO);
			bankTransactionDAO.updateBankTransaction(authDTO, bankTransactionDTO, customerDTO);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<BankTransactionDTO> getAllTransaction(AuthDTO authDTO, UserDTO userDTO, NamespaceDTO namespaceDTO, CustomerDTO customerDTO) {
		try {
			return bankTransactionDAO.getAllTransaction(authDTO, userDTO, namespaceDTO, customerDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting namespaces.", e);
		}
	}

	@Override
	public BankTransactionDTO getTransactionByCode(AuthDTO authDTO, NamespaceDTO namespaceDTO, CustomerDTO customerDTO, String code) {
		try {
			return bankTransactionDAO.getTransactionByCode(authDTO, namespaceDTO, customerDTO, code);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting namespaces.", e);
		}
	}

	@Override
	public List<BankTransactionDTO> executeFromToDateProcedure(AuthDTO authDTO, String code, String fromDate, String toDate) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCode(code);
		UserDTO user = new UserDTO();
		try {
			customerDTO = customerDAO.getCustomerByUser(authDTO, user, customerDTO);
			authDTO.setCustomerDTO(customerDTO);
			return bankTransactionDAO.executeFromToDateProcedure(authDTO, code, fromDate, toDate);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting from date todate.", e);
		}
	}
}
