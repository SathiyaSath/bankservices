package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;
import org.in.com.controller.io.CustomerIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.service.AuthService;
import org.in.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{authtoken}/customer")

public class CustomerController {

	@Autowired
	CustomerService customerService;
	@Autowired
	AuthService authService;

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CustomerIO updateCustomer(@PathVariable("authtoken") String authtoken, @RequestBody CustomerIO customerIO) {
		CustomerIO customerIo = new CustomerIO();
		CustomerDTO customerDto = new CustomerDTO();
		AuthDTO auth = authService.getAuth(authtoken);
		auth.setNamespaceDTO(auth.getNamespaceDTO());
		customerDto.setFirstName(customerIO.getFirstName());
		customerDto.setLastName(customerIO.getLastName());
		customerDto.setAddress(customerIO.getAddress());
		customerDto.setMobileNumber(customerIO.getMobileNumber());
		customerDto.setEmailId(customerIO.getEmailId());
		customerDto.setAadharNo(customerIO.getAadharNo());
		customerDto.setAccountType(customerIO.getAccountType());
		customerDto.setPancardNo(customerIO.getPancardNo());
		customerDto.setAccountNo(customerIO.getAccountNo());
		customerDto.setBranchName(customerIO.getBranchName());
		customerDto.setIfscCode(customerIO.getIfscCode());
		customerDto.setUsername(customerIO.getUsername());
		customerDto.setPassword(customerIO.getPassword());
		customerDto.setActiveFlag(customerIO.getActiveFlag());
		customerService.updateCustomer(auth, customerDto);
		customerIo.setActiveFlag(customerDto.getActiveFlag());
		customerIo.setCode(customerDto.getCode());
		return customerIo;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<CustomerIO> getAllCustomer(@PathVariable("authtoken") String authtoken) throws Exception {
		List<CustomerIO> customers = new ArrayList<CustomerIO>();
		NamespaceDTO namespaceDto = new NamespaceDTO();
		AuthDTO auth = authService.getAuth(authtoken);
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setNamespaceDTO(namespaceDto);
		List<CustomerDTO> customerDto = customerService.getAllCustomer(auth, customerDTO);
		for (CustomerDTO customerList : customerDto) {
			CustomerIO customerIO = new CustomerIO();
			customerIO.setCode(customerList.getCode());
			customerIO.setFirstName(customerList.getFirstName());
			customerIO.setLastName(customerList.getLastName());
			customerIO.setAddress(customerList.getAddress());
			customerIO.setMobileNumber(customerList.getMobileNumber());
			customerIO.setEmailId(customerList.getEmailId());
			customerIO.setAadharNo(customerList.getAadharNo());
			customerIO.setPancardNo(customerList.getPancardNo());
			customerIO.setAccountNo(customerList.getAccountNo());
			customerIO.setAccountType(customerList.getAccountType());
			customerIO.setBranchName(customerList.getBranchName());
			customerIO.setIfscCode(customerList.getIfscCode());
			customerIO.setActiveFlag(customerList.getActiveFlag());
		}
		return customers;
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CustomerIO getByCode(@PathVariable("authtoken") String authtoken, @PathVariable String code) throws Exception {
		CustomerIO customerIO = new CustomerIO();

		NamespaceDTO namespaceDto = new NamespaceDTO();
		AuthDTO auth = authService.getAuth(authtoken);
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setNamespaceDTO(namespaceDto);
		customerDTO = customerService.getCustomerDetails(auth, customerDTO, code);
		customerIO.setCode(customerDTO.getCode());
		customerIO.setFirstName(customerDTO.getFirstName());
		customerIO.setLastName(customerDTO.getLastName());
		customerIO.setAddress(customerDTO.getAddress());
		customerIO.setMobileNumber(customerDTO.getMobileNumber());
		customerIO.setEmailId(customerDTO.getEmailId());
		customerIO.setAadharNo(customerDTO.getAadharNo());
		customerIO.setPancardNo(customerDTO.getPancardNo());
		customerIO.setAccountNo(customerDTO.getAccountNo());
		customerIO.setAccountType(customerDTO.getAccountType());
		customerIO.setBranchName(customerDTO.getBranchName());
		customerIO.setIfscCode(customerDTO.getIfscCode());
		customerIO.setUsername(customerDTO.getUsername());
		customerIO.setPassword(customerDTO.getPassword());
		customerIO.setActiveFlag(customerDTO.getActiveFlag());
		return customerIO;
	}
}
