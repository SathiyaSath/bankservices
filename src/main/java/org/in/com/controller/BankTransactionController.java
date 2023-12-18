package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.in.com.controller.io.BankTransactionIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.in.com.service.AuthService;
import org.in.com.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{authtoken}/transaction")
public class BankTransactionController {

	@Autowired
	BankTransactionService bankTransactionService;
	@Autowired
	AuthService authService;

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BankTransactionIO updateBankTransaction(@PathVariable("authtoken") String authtoken, @RequestBody BankTransactionIO bankTransactionIO) {
		AuthDTO auth = authService.getAuth(authtoken);
		auth.setNamespaceDTO(auth.getNamespaceDTO());
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCode(bankTransactionIO.getCode());
		auth.setCustomerDTO(auth.getCustomerDTO());
		BankTransactionIO bankTransactions = new BankTransactionIO();
		BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
		customerDTO.setCode(bankTransactionIO.getCode());
		bankTransactionDTO.setMobileNumber(bankTransactionIO.getMobileNumber());
		bankTransactionDTO.setCustomerDTO(auth.getCustomerDTO());
		bankTransactionDTO.setNamespaceDTO(auth.getNamespaceDTO());
		bankTransactionDTO.setTransactionType(bankTransactionIO.getTransactionType());
		bankTransactionDTO.setTransactionAmount(bankTransactionIO.getTransactionAmount());
		bankTransactionDTO.setCreditAmount(bankTransactionIO.getCreditAmount());
		bankTransactionDTO.setDebitAmount(bankTransactionIO.getDebitAmount());
		bankTransactionDTO.setActiveFlag(bankTransactionIO.getActiveFlag());
		bankTransactionDTO.setMobileNumber(bankTransactionIO.getMobileNumber());
		bankTransactionService.updateBankTransaction(auth, bankTransactionDTO, customerDTO);
		bankTransactions.setCode(bankTransactionDTO.getCode());
		bankTransactions.setActiveFlag(bankTransactionDTO.getActiveFlag());
		return bankTransactions;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BankTransactionIO> getAllTransaction(@PathVariable("authtoken") String authtoken) throws Exception {
		List<BankTransactionIO> bankTransactions = new ArrayList<BankTransactionIO>();
		AuthDTO auth = authService.getAuth(authtoken);
		NamespaceDTO namespaceDTO = new NamespaceDTO();
		CustomerDTO customerDTO = new CustomerDTO();
		UserDTO userDTO = new UserDTO();
		List<BankTransactionDTO> bankTransactionDtoList = bankTransactionService.getAllTransaction(auth, userDTO, namespaceDTO, customerDTO);
		for (BankTransactionDTO bankTransactionDto : bankTransactionDtoList) {
			BankTransactionIO bankTransactionIO = new BankTransactionIO();
			bankTransactionIO.setCode(bankTransactionDto.getCode());
			bankTransactionIO.setTransactionType(bankTransactionDto.getTransactionType());
			bankTransactionIO.setTransactionAmount(bankTransactionDto.getTransactionAmount());
			bankTransactionIO.setCreditAmount(bankTransactionDto.getCreditAmount());
			bankTransactionIO.setDebitAmount(bankTransactionDto.getDebitAmount());
			bankTransactionIO.setActiveFlag(bankTransactionDto.getActiveFlag());
			bankTransactions.add(bankTransactionIO);
		}
		return bankTransactions;
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BankTransactionIO getTransactionByCode(@PathVariable("authtoken") String authtoken, @PathVariable String code) throws Exception {
		NamespaceDTO namespaceDTO = new NamespaceDTO();
		CustomerDTO customerDTO = new CustomerDTO();
		AuthDTO auth = authService.getAuth(authtoken);
		BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
		bankTransactionDTO.setCustomerDTO(auth.getCustomerDTO());
		BankTransactionDTO bankTransactionDto = bankTransactionService.getTransactionByCode(auth, namespaceDTO, customerDTO, code);
		BankTransactionIO bankTransactionIO = new BankTransactionIO();
		bankTransactionIO.setCode(bankTransactionDto.getCode());
		bankTransactionIO.setTransactionType(bankTransactionDto.getTransactionType());
		bankTransactionIO.setTransactionAmount(bankTransactionDto.getTransactionAmount());
		bankTransactionIO.setCreditAmount(bankTransactionDto.getCreditAmount());
		bankTransactionIO.setDebitAmount(bankTransactionDto.getDebitAmount());
		bankTransactionIO.setActiveFlag(bankTransactionDto.getActiveFlag());
		return bankTransactionIO;
	}

	@RequestMapping(value = "/transactionHistory/{customerCode}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<BankTransactionIO> executeFromToDateProcedure(@PathVariable("authtoken") String authtoken, @PathVariable("customerCode") String code, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) throws Exception {
		List<BankTransactionIO> bankTransactions = new ArrayList<>();
		AuthDTO auth = authService.getAuth(authtoken);
		List<BankTransactionDTO> bankTransactionDtoList = bankTransactionService.executeFromToDateProcedure(auth, code, fromDate, toDate);
		for (BankTransactionDTO bankTransactionDtos : bankTransactionDtoList) {
			BankTransactionIO bankTransactionIo = new BankTransactionIO();
			bankTransactionIo.setCode(bankTransactionDtos.getCode());
			bankTransactionIo.setTransactionType(bankTransactionDtos.getTransactionType());
			bankTransactionIo.setTransactionAmount(bankTransactionDtos.getTransactionAmount());
			bankTransactionIo.setCreditAmount(bankTransactionDtos.getCreditAmount());
			bankTransactionIo.setDebitAmount(bankTransactionDtos.getDebitAmount());
			bankTransactionIo.setActiveFlag(bankTransactionDtos.getActiveFlag());
			bankTransactions.add(bankTransactionIo);
		}
		return bankTransactions;
	}
}
