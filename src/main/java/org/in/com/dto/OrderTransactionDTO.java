package org.in.com.dto;

import java.math.BigDecimal;

import org.in.com.dto.enumuration.PaymentType;
import org.in.com.dto.enumuration.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderTransactionDTO extends BaseDTO {
	private String code;
	private Status status;
	private PaymentType paymentType;
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
	private int activeFlag;
	private String returnUrl;
	private String callbackUrl;
	private BigDecimal refundAmount;

	public String getGeneratedString() {
		final int GENERATED_STRING_LENGTH = 6;
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
		StringBuilder stringBuilder = new StringBuilder(GENERATED_STRING_LENGTH);
		for (int i = 0; i < GENERATED_STRING_LENGTH; i++) {
			int index = (int) (alphaNumericString.length() * Math.random());
			stringBuilder.append(alphaNumericString.charAt(index));
		}
		return stringBuilder.toString();
	}

	public String getOrderCodeString() {
		int n = 6;
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
		StringBuilder stringBuilder = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (alphaNumericString.length() * Math.random());
			stringBuilder.append(alphaNumericString.charAt(index));
		}
		return stringBuilder.toString();
	}

}
