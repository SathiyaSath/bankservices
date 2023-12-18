package org.in.com.dto;

import java.math.BigDecimal;

import org.in.com.dto.enumuration.TransactionType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BankTransactionDTO extends BaseDTO {
	private UserDTO userDTO;
	private CustomerDTO customerDTO;
	private NamespaceDTO namespaceDTO;
	private TransactionType transactionType;
	private BigDecimal transactionAmount;
	private BigDecimal creditAmount;
	private BigDecimal debitAmount;
	private String mobileNumber;
	private String authToken;

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
}
