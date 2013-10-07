package uk.ac.cam.cl.dtg.teaching.api;

public class ItemNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048104173342854172L;

	public ItemNotFoundException() {

	}

	public ItemNotFoundException(String message) {
		super(message);

	}

	public ItemNotFoundException(Throwable cause) {
		super(cause);

	}

	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public ItemNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
