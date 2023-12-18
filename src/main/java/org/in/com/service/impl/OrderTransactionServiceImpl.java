package org.in.com.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.in.com.dao.OrderTransactionDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.OrderTransactionDTO;
import org.in.com.service.OrderTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderTransactionServiceImpl implements OrderTransactionService {

	@Autowired
	OrderTransactionDAO orderTransactionDAO;

	@Override
	public void updateOrderTransaction(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO) {
		try {
			orderTransactionDAO.updateOrderTransaction(authDTO, orderTransactionDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting order details", e);
		}
	}

	@Override
	public List<OrderTransactionDTO> getOrderDetails(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO) {
		List<OrderTransactionDTO> orderTransactionList = new ArrayList<>();
		try {
			orderTransactionList = orderTransactionDAO.getOrderDetails(authDTO, orderTransactionDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting order details", e);
		}
		return orderTransactionList;
	}

	@Override
	public List<OrderTransactionDTO> validateOrder(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO) {
		List<OrderTransactionDTO> orderTransactionList = new ArrayList<>();
		BigDecimal amount = null;
		String orderType = null;
		try {
			if (orderTransactionDTO.getAmount() != null) {
				amount = orderTransactionDTO.getAmount();
				orderType = orderTransactionDTO.getOrderType();
			}
			orderTransactionList = orderTransactionDAO.getOrderDetails(authDTO, orderTransactionDTO);
			if (!orderTransactionList.isEmpty()) {
				OrderTransactionDTO storedTransaction = orderTransactionList.get(0);
				if (orderType.equals(storedTransaction.getOrderType()) && amount.compareTo(storedTransaction.getAmount()) == 0) {
					System.out.println("Valid Order Transaction");
				}
				else {
					System.out.println("Invalid Order Transaction");
				}
			}
			else {
				System.out.println("Order Transaction not found");
			}
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting order details", e);
		}
		return orderTransactionList;
	}

	@Override
	public void refundOrder(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO) {
		try {
			orderTransactionDAO.refundOrder(authDTO, orderTransactionDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting order details", e);
		}
	}
}
