package org.in.com.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.OrderTransactionDTO;
import org.in.com.dto.enumuration.PaymentType;
import org.in.com.dto.enumuration.Status;
import org.springframework.stereotype.Repository;
import lombok.Cleanup;

@Repository
public class OrderTransactionDAO {

	public void updateOrderTransaction(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO) throws Exception {
		@Cleanup
		Connection connection = null;
		try {
			connection = ConnectionDAO.getConnection();
			String sql = "CALL EZEE_SP_ORDERTRANSACTION_ID(?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, orderTransactionDTO.getGeneratedString());
			statement.setString(2, orderTransactionDTO.getOrderCodeString());
			statement.setInt(3, orderTransactionDTO.getStatus().getId());
			statement.setInt(4, orderTransactionDTO.getPaymentType().getId());
			statement.setString(5, orderTransactionDTO.getCustomerName());
			statement.setString(6, orderTransactionDTO.getMerchantName());
			statement.setString(7, orderTransactionDTO.getOrderType());
			statement.setBigDecimal(8, orderTransactionDTO.getAmount());
			statement.setString(9, orderTransactionDTO.getUserCode());
			statement.setString(10, orderTransactionDTO.getBankRefNo());
			statement.setString(11, orderTransactionDTO.getBankName());
			statement.setString(12, orderTransactionDTO.getNameOnCard());
			statement.setString(13, orderTransactionDTO.getCardType());
			statement.setString(14, orderTransactionDTO.getCardNumber());
			statement.setString(15, orderTransactionDTO.getMobileNumber());
			statement.setString(16, orderTransactionDTO.getReturnUrl());
			statement.setString(17, orderTransactionDTO.getCallbackUrl());
			statement.setInt(18, orderTransactionDTO.getActiveFlag());
			statement.setInt(19, authDTO.getUserDTO().getId());
			statement.executeUpdate();
		}
		catch (Exception e) {
			throw new RuntimeException("error occure update details to order", e);
		}
	}

	public List<OrderTransactionDTO> getOrderDetails(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO) throws Exception {
		List<OrderTransactionDTO> list = new ArrayList<>();
		@Cleanup
		Connection connection = null;
		@Cleanup
		PreparedStatement preparedStatement = null;
		@Cleanup
		ResultSet resultSet = null;
		try {
			connection = ConnectionDAO.getConnection();
			if (orderTransactionDTO.getOrderCode() != null) {
				String sql = "SELECT code, status_id, payment_type_id, date_of_transaction, order_code, customer_name, merchant_name, order_type, amount, user_code, bank_ref_no, bank_name, name_on_card, card_type, card_number, return_url, callback_url, active_flag, updated_by ,updated_at FROM order_transaction WHERE active_flag = 1 AND order_code = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, orderTransactionDTO.getOrderCode());
			}
			else {
				String sql = "SELECT code, status_id, payment_type_id, date_of_transaction, order_code, customer_name, merchant_name, order_type, amount, user_code, bank_ref_no, bank_name, name_on_card, card_type, card_number, return_url, callback_url, active_flag, updated_by ,updated_at FROM order_transaction WHERE active_flag = 1 AND (order_code = ? OR bank_ref_no = ? OR order_type = ? OR phone = ?)";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, orderTransactionDTO.getOrderCode());
				preparedStatement.setString(2, orderTransactionDTO.getBankRefNo());
				preparedStatement.setString(3, orderTransactionDTO.getOrderType());
				preparedStatement.setString(4, orderTransactionDTO.getMobileNumber());
			}
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				orderTransactionDTO.setCode(resultSet.getString("code"));
				int statusId = resultSet.getInt("status_id");
				orderTransactionDTO.setStatus(Status.getById(statusId));
				int paymentId = resultSet.getInt("payment_type_id");
				orderTransactionDTO.setPaymentType(PaymentType.getById(paymentId));
				orderTransactionDTO.setOrderCode(resultSet.getString("order_code"));
				orderTransactionDTO.setDateOfTransaction(resultSet.getString("date_of_transaction"));
				orderTransactionDTO.setCustomerName(resultSet.getString("customer_name"));
				orderTransactionDTO.setMerchantName(resultSet.getString("merchant_name"));
				orderTransactionDTO.setOrderType(resultSet.getString("order_type"));
				orderTransactionDTO.setAmount(resultSet.getBigDecimal("amount"));
				orderTransactionDTO.setUserCode(resultSet.getString("user_code"));
				orderTransactionDTO.setBankRefNo(resultSet.getString("bank_ref_no"));
				orderTransactionDTO.setBankName(resultSet.getString("bank_name"));
				orderTransactionDTO.setCardNumber(resultSet.getString("card_number"));
				orderTransactionDTO.setNameOnCard(resultSet.getString("name_on_card"));
				orderTransactionDTO.setCardType(resultSet.getString("card_type"));
				orderTransactionDTO.setCardType(resultSet.getString("return_url"));
				orderTransactionDTO.setCardType(resultSet.getString("callback_url"));
				orderTransactionDTO.setActiveFlag(resultSet.getInt("active_flag"));
				list.add(orderTransactionDTO);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("error occure get details from order", e);
		}
		return list;
	}

	public void refundOrder(AuthDTO authDTO, OrderTransactionDTO orderTransactionDTO) throws Exception {
		@Cleanup
		Connection connection = null;
		@Cleanup
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionDAO.getConnection();
			if (orderTransactionDTO.getRefundAmount() != null && orderTransactionDTO.getRefundAmount().compareTo(BigDecimal.ZERO) > 0 && orderTransactionDTO.getRefundAmount().compareTo(orderTransactionDTO.getAmount()) <= 0) {
				String updateOriginalOrderSql = "UPDATE order_transaction SET amount = amount - ? ,status_id = ? ,refund_amount = ? ,updated_at =NOW() WHERE code = ?";
				preparedStatement = connection.prepareStatement(updateOriginalOrderSql);
				preparedStatement.setBigDecimal(1, orderTransactionDTO.getRefundAmount());
				preparedStatement.setInt(2, orderTransactionDTO.getStatus().getId());
				preparedStatement.setBigDecimal(3, orderTransactionDTO.getRefundAmount());
				preparedStatement.setString(4, orderTransactionDTO.getCode());
				preparedStatement.executeUpdate();
			}
			else {
				System.out.println("Invalid refund amount");
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Invalid refund amount", e);
		}
	}
}