package co.com.kimera.dronescheduler.drone.impl;

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
