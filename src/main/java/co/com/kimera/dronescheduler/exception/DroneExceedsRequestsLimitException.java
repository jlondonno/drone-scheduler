package co.com.kimera.dronescheduler.exception;

/**
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class DroneExceedsRequestsLimitException
 * @date May 10, 2020
 *
 */
public class DroneExceedsRequestsLimitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DroneExceedsRequestsLimitException() {
		super();
	}

	public DroneExceedsRequestsLimitException(String s) {
		super(s);
	}

	public DroneExceedsRequestsLimitException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public DroneExceedsRequestsLimitException(Throwable throwable) {
		super(throwable);
	}
}
