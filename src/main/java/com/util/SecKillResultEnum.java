package com.util;

public enum SecKillResultEnum {
	
	SUCCCESS(1),REPEAT(2),CLOSE(3),ERROR(-1);
	
	private int resultCode;
	
	SecKillResultEnum(int resultCode){
		this.resultCode = resultCode;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
