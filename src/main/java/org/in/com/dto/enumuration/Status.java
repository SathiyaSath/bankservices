package org.in.com.dto.enumuration;

public enum Status {
	PENDING(1, "PND", "Pending"), PAID(2, "PAD", "Paid"), CANCEL(3, "CNL", "Cancel");

	private final int id;
	private final String code;
	private final String name;

	Status(int id, String code, String name) {
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

	public static Status getById(int id) {
		for (Status status : Status.values()) {
			if (status.getId() == (id)) {
				return status;
			}
		}
		return null;
	}

	public static Status getByCode(String code) {
		for (Status status : Status.values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		return null;

	}
}