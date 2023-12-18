package org.in.com.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.in.com.dao.CustomerDAO;
import org.in.com.dto.AuthDTO;

import org.in.com.dto.CustomerDTO;
import org.in.com.dto.UserDTO;
import org.in.com.rediscache.CustomerCacheManager;
import org.in.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private CustomerCacheManager customerCacheManager;

	public void updateCustomer(AuthDTO authDTO, CustomerDTO customerDTO) {
		try {
			customerDAO.updateCustomer(authDTO, customerDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting  Customer.", e);
		}
	}

	@Override
	public List<CustomerDTO> getAllCustomer(AuthDTO authDTO, CustomerDTO customerDTO) {

		try {
			return customerDAO.getAllCustomer(authDTO, customerDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting  Customer.", e);
		}

	}

	@Override
	public CustomerDTO getCustomerDetails(AuthDTO authDTO, CustomerDTO customerDTO, String code) {
		CustomerDTO customers = customerCacheManager.getCustomerData(authDTO, customerDTO, code);
		if (customers == null) {
			try {
				customers = customerDAO.getCustomerDetails(authDTO, customerDTO, code);
			}
			catch (Exception e) {
				throw new RuntimeException("An error occurred while getting  Customer.", e);
			}
		}
		else {
			customerCacheManager.putCustomerData(authDTO, customers, code);
		}

		System.out.println("redis customer data" + customers);
		return customers;
	}

	@Override
	public CustomerDTO getCustomerByUser(AuthDTO authDTO, UserDTO user, CustomerDTO customerDTO) {
		CustomerDTO customers = customerCacheManager.getCustomerData(authDTO, customerDTO, customerDTO.getCode());
		if (customers == null) {
			try {
				customers = customerDAO.getCustomerByUser(authDTO, user, customerDTO);
			}
			catch (SQLException e) {
				throw new RuntimeException("An error occurred while getting  Customer.", e);
			}
		}
		else {
			customerCacheManager.putCustomerData(authDTO, customers, customerDTO.getCode());
		}
		return customers;
	}
}
