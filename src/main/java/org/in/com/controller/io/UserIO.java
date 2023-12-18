package org.in.com.controller.io;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserIO extends BaseIO {
	private String firstName;
	private String lastName;
	private String userRole;
	private String address;
	private String username;
	private String password;
}
