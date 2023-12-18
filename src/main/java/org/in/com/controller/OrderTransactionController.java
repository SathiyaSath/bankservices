package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.in.com.controller.io.BaseIO;
import org.in.com.controller.io.OrderTransactionIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.OrderTransactionDTO;
import org.in.com.dto.enumuration.*;

import org.in.com.service.AuthService;
import org.in.com.service.OrderTransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{namespaceCode}/{username}/{apitoken}/order")

public class OrderTransactionController {
	@Autowired
	private OrderTransactionService orderTransactionService;
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrderTransactionIO updateOrderTransaction(@PathVariable("namespaceCode") String namespaceCode, @PathVariable("username") String username, @PathVariable("apitoken") String apitoken, @RequestBody OrderTransactionIO orderTransactionIO) {
		OrderTransactionIO updatedOrderTransactionIO = new OrderTransactionIO();
		AuthDTO authDTO = authService.getAuth(namespaceCode, username, apitoken);
		OrderTransactionDTO orderTransactionDTO = new OrderTransactionDTO();
		orderTransactionDTO.setCustomerName(orderTransactionIO.getCustomerName());
		orderTransactionDTO.setOrderCode(orderTransactionIO.getOrderCode());
		orderTransactionDTO.setStatus(Status.getByCode(orderTransactionIO.getStatus().getCode()));
		orderTransactionDTO.setPaymentType(PaymentType.getByCode(orderTransactionIO.getPaymentType().getCode()));
		orderTransactionDTO.setMerchantName(orderTransactionIO.getMerchantName());
		orderTransactionDTO.setOrderType(orderTransactionIO.getOrderType());
		orderTransactionDTO.setAmount(orderTransactionIO.getAmount());
		orderTransactionDTO.setUserCode(orderTransactionIO.getUserCode());
		orderTransactionDTO.setBankRefNo(orderTransactionIO.getBankRefNo());
		orderTransactionDTO.setBankName(orderTransactionIO.getBankName());
		orderTransactionDTO.setNameOnCard(orderTransactionIO.getNameOnCard());
		orderTransactionDTO.setCardType(orderTransactionIO.getOrderType());
		orderTransactionDTO.setCardNumber(orderTransactionIO.getCardNumber());
		orderTransactionDTO.setMobileNumber(orderTransactionIO.getMobileNumber());
		orderTransactionDTO.setReturnUrl(orderTransactionIO.getReturnUrl());
		orderTransactionDTO.setCallbackUrl(orderTransactionIO.getCallbackUrl());
		orderTransactionDTO.setActiveFlag(orderTransactionIO.getActiveFlag());
		orderTransactionService.updateOrderTransaction(authDTO, orderTransactionDTO);
		updatedOrderTransactionIO.setActiveFlag(orderTransactionDTO.getActiveFlag());
		updatedOrderTransactionIO.setCode(orderTransactionDTO.getCode());
		return updatedOrderTransactionIO;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<OrderTransactionIO> getOrderDetails(@PathVariable("namespaceCode") String namespaceCode, @PathVariable("username") String username, @PathVariable("apitoken") String apitoken, @RequestBody OrderTransactionIO orderTransactionIO) {
		List<OrderTransactionIO> orderTransactionList = new ArrayList<>();
		AuthDTO authDTO = authService.getAuth(apitoken);
		OrderTransactionDTO orderTransactionDTO = new OrderTransactionDTO();
		orderTransactionDTO.setCode(orderTransactionIO.getCode());
		orderTransactionDTO.setOrderCode(orderTransactionIO.getOrderCode());
		orderTransactionDTO.setBankRefNo(orderTransactionIO.getBankRefNo());
		orderTransactionService.getOrderDetails(authDTO, orderTransactionDTO);
		if (orderTransactionDTO.getCode() != null) {
			OrderTransactionIO responseIO = new OrderTransactionIO();
			BaseIO statusBaseIO = new BaseIO();
			BaseIO paymentTypeBaseIO = new BaseIO();
			responseIO.setCode(orderTransactionDTO.getCode());
			responseIO.setOrderCode(orderTransactionDTO.getOrderCode());
			statusBaseIO.setCode(orderTransactionDTO.getStatus().getCode());
			statusBaseIO.setName(orderTransactionDTO.getStatus().getName());
			responseIO.setStatus(statusBaseIO);
			paymentTypeBaseIO.setCode(orderTransactionDTO.getPaymentType().getCode());
			paymentTypeBaseIO.setName(orderTransactionDTO.getPaymentType().getName());
			responseIO.setPaymentType(paymentTypeBaseIO);
			responseIO.setDateOfTransaction(orderTransactionDTO.getDateOfTransaction());
			responseIO.setCustomerName(orderTransactionDTO.getCustomerName());
			responseIO.setMerchantName(orderTransactionDTO.getMerchantName());
			responseIO.setOrderType(orderTransactionDTO.getOrderType());
			responseIO.setAmount(orderTransactionDTO.getAmount());
			responseIO.setUserCode(orderTransactionDTO.getUserCode());
			responseIO.setBankRefNo(orderTransactionDTO.getBankRefNo());
			responseIO.setBankName(orderTransactionDTO.getBankName());
			responseIO.setCardNumber(orderTransactionDTO.getCardNumber());
			responseIO.setNameOnCard(orderTransactionDTO.getNameOnCard());
			responseIO.setCardType(orderTransactionDTO.getCardType());
			responseIO.setMobileNumber(orderTransactionDTO.getMobileNumber());
			responseIO.setReturnUrl(orderTransactionDTO.getReturnUrl());
			responseIO.setCallbackUrl(orderTransactionDTO.getCallbackUrl());
			responseIO.setActiveFlag(orderTransactionDTO.getActiveFlag());
			orderTransactionList.add(responseIO);
		}
		return orderTransactionList;
	}

	@RequestMapping(value = "/validate/order", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrderTransactionIO validateOrder(@PathVariable("namespaceCode") String namespaceCode, @PathVariable("username") String username, @PathVariable("apitoken") String apitoken, @RequestBody OrderTransactionIO orderTransactionIO) {
		OrderTransactionDTO orderTransactionDTO = new OrderTransactionDTO();
		OrderTransactionIO orderTransactionResult = new OrderTransactionIO();
		AuthDTO auth = authService.getAuth(apitoken);
		orderTransactionDTO.setCode(orderTransactionIO.getCode());
		orderTransactionDTO.setOrderCode(orderTransactionIO.getOrderCode());
		orderTransactionDTO.setOrderType(orderTransactionIO.getOrderType());
		orderTransactionDTO.setAmount(orderTransactionIO.getAmount());
		orderTransactionService.validateOrder(auth, orderTransactionDTO);
		orderTransactionResult.setActiveFlag(orderTransactionDTO.getActiveFlag());
		orderTransactionResult.setCode(orderTransactionDTO.getCode());
		return orderTransactionResult;
	}

	@RequestMapping(value = "/refund", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public OrderTransactionIO refundOrder(@PathVariable("namespaceCode") String namespaceCode, @PathVariable("username") String username, @PathVariable("apitoken") String apitoken, @RequestBody OrderTransactionIO orderTransactionIO) {
		OrderTransactionDTO orderTransactionDTO = new OrderTransactionDTO();
		AuthDTO authDTO = authService.getAuth(apitoken);
		orderTransactionDTO.setCode(orderTransactionIO.getCode());
		orderTransactionDTO.setOrderCode(orderTransactionIO.getOrderCode());
		orderTransactionDTO.setOrderType(orderTransactionIO.getOrderType());
		orderTransactionDTO.setAmount(orderTransactionIO.getAmount());
		orderTransactionDTO.setStatus(Status.getByCode(orderTransactionIO.getStatus().getCode()));
		orderTransactionDTO.setRefundAmount(orderTransactionIO.getRefundAmount());
		orderTransactionService.refundOrder(authDTO, orderTransactionDTO);
		OrderTransactionIO orderTransactionResult = new OrderTransactionIO();
		orderTransactionResult.setCode(orderTransactionDTO.getCode());
		orderTransactionResult.setActiveFlag(orderTransactionDTO.getActiveFlag());
		return orderTransactionResult;
	}
}