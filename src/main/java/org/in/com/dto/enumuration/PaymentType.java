package org.in.com.dto.enumuration;

public enum PaymentType {
	UPI(1, "UPI", "Unified Payment Interface"), CARD(2, "Card", "Credit/Debit Card"), WALLET(3, "Wallet", "Digital Wallet"), GPAY(4, "GPay", "Google Pay"), OTHERS(5, "Others", "Other Payment Methods");
	private final int id;
	private final String code;
	private final String name;

	PaymentType(int id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static PaymentType getById(int id) {
		for (PaymentType paymentType : PaymentType.values()) {
			if (paymentType.getId() == (id)) {
				return paymentType;
			}
		}
		return null;
	}

	public static PaymentType getByCode(String code) {
		for (PaymentType paymentType : PaymentType.values()) {
			if (paymentType.getCode().equals(code)) {
				return paymentType;
			}
		}
		return null;

	}
}
