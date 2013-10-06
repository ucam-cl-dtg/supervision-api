package uk.ac.cam.cl.dtg.teaching.api;

public class ApiFailureMessage {

	private boolean success = false;

	private String message = null;
	private ApiFailureMessage cause = null;

	public ApiFailureMessage() {
		super();
	}

	public ApiFailureMessage(Throwable t) {
		super();
		this.message = t.getMessage();
		if (t.getCause() != null) {
			this.cause = new ApiFailureMessage(t.getCause());
		}
	}

	public ApiFailureMessage(String message, Throwable t) {
		super();
		this.message = message;
		this.cause = new ApiFailureMessage(t);
	}

	public ApiFailureMessage(String message, ApiFailureMessage cause) {
		super();
		this.message = message;
		this.cause = cause;
	}

	public ApiFailureMessage(String message) {
		super();
		this.message =message;
		this.cause = null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ApiFailureMessage getCause() {
		return cause;
	}

	public void setCause(ApiFailureMessage cause) {
		this.cause = cause;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
