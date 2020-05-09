package co.com.kimera.dronescheduler.drone.impl;

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
