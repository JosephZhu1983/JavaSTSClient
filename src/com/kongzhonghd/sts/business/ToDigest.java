package com.kongzhonghd.sts.business;

public class ToDigest {

	private ToDigestType type;
	private String digest;

	public ToDigest(ToDigestType type, String digest) {
		this.type = type;
		this.digest = digest;
	}

	public ToDigestType getType() {
		return type;
	}

	public String getDigest() {
		return digest;
	}

	public String build() {
		return type.getPrefix() + digest;
	}
}
