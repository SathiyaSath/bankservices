package org.in.com.controller.io;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerIO extends BaseIO {
	private String firstName;
	private String lastName;
	private String address;
	private String mobileNumber;
	private String emailId;
	private String aadharNo;
	private String pancardNo;
	private String accountNo;
	private String accountType;
	private String branchName;
	private String ifscCode;
	private String username;
	private String password;
}
