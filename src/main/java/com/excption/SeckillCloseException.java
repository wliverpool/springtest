package com.excption;

public class SeckillCloseException extends SecKillException{

	private static final long serialVersionUID = -3820819386927660819L;
	
	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

}
