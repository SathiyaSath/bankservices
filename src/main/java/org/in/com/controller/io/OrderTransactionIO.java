package org.in.com.controller.io;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderTransactionIO extends BaseIO {
	private String dateOfTransaction;
	private String orderCode;
	private String customerName;
	private String merchantName;
	private String orderType;
	private BigDecimal amount;
	private String userCode;
	private String bankRefNo;
	private String bankName;
	private String nameOnCard;
	private String cardType;
	private String cardNumber;
	private String mobileNumber;
	private String returnUrl;
	private String callbackUrl;
	private BaseIO status;
	private BaseIO paymentType;
	private BigDecimal refundAmount;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
}
