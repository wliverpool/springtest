package com.excption;

public class SeckillRepeatException extends SecKillException{

	private static final long serialVersionUID = -6771745760857556664L;
	
	public SeckillRepeatException(String message) {
		super(message);
	}

	public SeckillRepeatException(String message, Throwable cause) {
		super(message, cause);
	}

}
