package co.com.kimera.dronescheduler.drone.impl;

/**
 * Enum that is helpful to define the orientation of the drone
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class CardinalDirection
 * @date May 10, 2020
 *
 */
public enum CardinalDirection {

	WEST("West"), EAST("East"), NORTH("North"), SOUTH("South");

	private String name;

	private CardinalDirection(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
