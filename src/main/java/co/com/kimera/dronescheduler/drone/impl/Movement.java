package co.com.kimera.dronescheduler.drone.impl;

/**
 * Enum that is helpful to clasify movements from the drone
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class Movement
 * @date May 10, 2020
 *
 */
public enum Movement {

	A("A"), I("I"), D("D");

	private String name;

	private Movement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
