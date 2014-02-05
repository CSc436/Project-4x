package com.fourx.buffs;

public enum TYPE {
	ALL("ALL"), INFANTRY("INFANTRY");
	
	private String value;
	private TYPE(String string) {
		this.value = string;
	}
	
	public String getValue() {
		return value;
	}
}

