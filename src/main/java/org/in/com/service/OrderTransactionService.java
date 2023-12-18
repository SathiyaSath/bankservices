package org.in.com.service;

import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.OrderTransactionDTO;

public interface OrderTransactionService {

	public void updateOrderTransaction(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO);

	public List<OrderTransactionDTO> getOrderDetails(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO);

	public List<OrderTransactionDTO> validateOrder(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO);

	public void refundOrder(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO);
}
