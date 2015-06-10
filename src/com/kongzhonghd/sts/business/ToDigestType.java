package com.kongzhonghd.sts.business;

public enum ToDigestType {
	Digest("$digest:");
	private String prefix;

	ToDigestType(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return this.prefix;
	}
}
