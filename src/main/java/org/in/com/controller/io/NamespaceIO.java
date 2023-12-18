package org.in.com.controller.io;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamespaceIO extends BaseIO {
	private String name;
	private String code;
	private String address;
}
