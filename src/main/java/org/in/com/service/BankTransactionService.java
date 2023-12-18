package org.in.com.service;

import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;

public interface BankTransactionService {

	public void updateBankTransaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO,CustomerDTO customerDTO);

	public List<BankTransactionDTO> getAllTransaction(AuthDTO authDTO, UserDTO userDTO, NamespaceDTO namespaceDTO, CustomerDTO customerDTO);

	public BankTransactionDTO getTransactionByCode(AuthDTO authDTO, NamespaceDTO namespaceDTO, CustomerDTO customerDTO, String code);

	public List<BankTransactionDTO> executeFromToDateProcedure(AuthDTO authDTO, String code, String fromDate, String toDate);
}
