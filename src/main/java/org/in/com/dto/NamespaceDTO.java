package org.in.com.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamespaceDTO extends BaseDTO {
	private String name;
	private String address;

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
