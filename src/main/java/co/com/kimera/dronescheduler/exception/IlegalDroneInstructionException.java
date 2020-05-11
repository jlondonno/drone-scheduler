package co.com.kimera.dronescheduler.exception;

/**
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class IlegalDroneInstructionException
 * @date May 10, 2020
 */
public class IlegalDroneInstructionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IlegalDroneInstructionException() {
		super();
	}

	public IlegalDroneInstructionException(String s) {
		super(s);
	}

	public IlegalDroneInstructionException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public IlegalDroneInstructionException(Throwable throwable) {
		super(throwable);
	}
}
