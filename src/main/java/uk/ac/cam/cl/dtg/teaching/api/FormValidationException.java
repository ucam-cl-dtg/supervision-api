package uk.ac.cam.cl.dtg.teaching.api;

import java.util.LinkedList;
import java.util.List;

public class FormValidationException extends Exception {

	private static final long serialVersionUID = -1569528550290163513L;

	private List<String> errors = new LinkedList<String>();
	
	public FormValidationException() {
		super();
	}

	public FormValidationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		errors.add(message);
	}

	public FormValidationException(String message, Throwable cause) {
		super(message, cause);
		errors.add(message);
	}

	public FormValidationException(String message) {
		super(message);
		errors.add(message);
	}

	public FormValidationException(Throwable cause) {
		super(cause);
	}

	public void addMessage(String message) {
		errors.add(message);
	}
}
