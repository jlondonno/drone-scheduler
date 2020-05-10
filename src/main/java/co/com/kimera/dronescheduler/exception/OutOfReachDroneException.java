package co.com.kimera.dronescheduler.exception;

/**
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class OutOfReachDroneException
 * @date May 10, 2020
 */
public class OutOfReachDroneException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OutOfReachDroneException() {
		super();
	}

	public OutOfReachDroneException(String s) {
		super(s);
	}

	public OutOfReachDroneException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public OutOfReachDroneException(Throwable throwable) {
		super(throwable);
	}
}
