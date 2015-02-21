package com.lugq.app.network;

public enum BaseResult {

	Success("base.succ"), 
	Failure("base.failure");
	
	public String i18nCode;
	
	private BaseResult(String i18nCode) {
		this.i18nCode = i18nCode;
	}
}
