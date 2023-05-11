package org.cos.common.exception;

public class VerifyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1419459563110058385L;


	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public VerifyException() {
		// TODO Auto-generated constructor stub
	}

	public VerifyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public VerifyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public VerifyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public VerifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public VerifyException(int code, String message) {
		super(message);
		this.code = code;
		// TODO Auto-generated constructor stub
	}
}
