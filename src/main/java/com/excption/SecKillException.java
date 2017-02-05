package com.excption;

public class SecKillException extends RuntimeException {

	private static final long serialVersionUID = -6188144373125565206L;

	public SecKillException(String message) {
		super(message);
	}

	public SecKillException(String message, Throwable cause) {
		super(message, cause);
	}

}
