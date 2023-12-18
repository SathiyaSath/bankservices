package org.in.com.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {
	private NamespaceDTO namespaceDTO;
	private CustomerDTO customerDTO;
	private String firstName;
	private String lastName;
	private String userRole;
	private String address;
	private String username;
	private String password;
	private String authtoken;
	private String apitoken;

	public String GenerateUniqueID() {
		String randomUUID = "";
		try {
			UUID uuid = UUID.randomUUID();
			randomUUID = uuid.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return randomUUID;
	}
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
