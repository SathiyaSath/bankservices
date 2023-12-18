package org.in.com.controller.io;

import java.math.BigDecimal;

import org.in.com.dto.enumuration.TransactionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BankTransactionIO extends BaseIO {
	private TransactionType transactionType;
	private BigDecimal transactionAmount;
	private BigDecimal creditAmount;
	private BigDecimal debitAmount;
	private String mobileNumber;
}
