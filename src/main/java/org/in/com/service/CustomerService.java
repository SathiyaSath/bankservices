package org.in.com.service;

import java.util.List;

import org.in.com.dto.AuthDTO;

import org.in.com.dto.CustomerDTO;
import org.in.com.dto.UserDTO;

public interface CustomerService {

	public void updateCustomer(AuthDTO authDTO, CustomerDTO customerDTO);

	List<CustomerDTO> getAllCustomer(AuthDTO authDTO, CustomerDTO customerDTO);

	public CustomerDTO getCustomerDetails(AuthDTO authDTO, CustomerDTO customerDTO, String code);

	public CustomerDTO getCustomerByUser(AuthDTO authDTO, UserDTO user, CustomerDTO customerDTO);

}
