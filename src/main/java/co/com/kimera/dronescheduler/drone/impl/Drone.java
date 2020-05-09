package co.com.kimera.dronescheduler.drone.impl;

import java.util.ArrayList;
import java.util.List;

import co.com.kimera.dronescheduler.drone.DroneMovement;

public class Drone implements DroneMovement {

	private static final int MAX_NUMBER_REQUESTS_PER_TRIP = 3;
	private static final int MAX_NUMBER_BLOCKS_TO_MOVE = 10;

	private List<String> droneRequests = new ArrayList<String>();
	private List<String> deliveryInformation = new ArrayList<String>();
	private CardinalDirection pointingTo;
	private Coordinate startingPosition;
	private Coordinate currentPosition;
	private Integer requestsLimitPerDron;

	private int maxPointX;
	private int minPointX;

	private int maxPointY;
	private int minPointY;

	public Drone() {
		pointingTo = CardinalDirection.NORTH;
		startingPosition = new Coordinate(0, 0);
		currentPosition = new Coordinate(0, 0);
		
		this.maxPointX = MAX_NUMBER_BLOCKS_TO_MOVE;
		this.minPointX = - MAX_NUMBER_BLOCKS_TO_MOVE;
		this.maxPointY = MAX_NUMBER_BLOCKS_TO_MOVE;
		this.minPointY = - MAX_NUMBER_BLOCKS_TO_MOVE;
	}

	public Drone(CardinalDirection pointingTo, Coordinate startingPosition, Integer requestsLimitPerDron,
			Integer blocksAroundToMoveLimit) {
		this.pointingTo = pointingTo;
		this.startingPosition = startingPosition;
		this.currentPosition = startingPosition;
		this.requestsLimitPerDron = requestsLimitPerDron;

		int posXStartingPosition = getStartingPointCoordinate().getX();
		int posYStartingPosition = getStartingPointCoordinate().getY();

		this.maxPointX = posXStartingPosition + blocksAroundToMoveLimit;
		this.minPointX = posXStartingPosition - blocksAroundToMoveLimit;
		this.maxPointY = posYStartingPosition + blocksAroundToMoveLimit;
		this.minPointY = posYStartingPosition - blocksAroundToMoveLimit;
	}

	public Drone(List<String> requests) {
		this.droneRequests = requests;
	}

	public void addRequest(String request) {
		int limit = requestsLimitPerDron != null ? requestsLimitPerDron : MAX_NUMBER_REQUESTS_PER_TRIP;
		if (droneRequests.size() >= limit) {
			throw new RuntimeException("Drone is not able to load another request");
		}
		droneRequests.add(request);
	}

	private void moveForward() {
		int x = getCurrentPosition().getX();
		int y = getCurrentPosition().getY();
		switch (pointingTo) {
		case NORTH:
			y++;
			break;
		case EAST:
			x++;
			break;
		case SOUTH:
			y--;
			break;
		default:
			x--;
		}

		if (isValidMovement()) {
			getCurrentPosition().setX(x);
			getCurrentPosition().setY(y);
		} else {
			abortDelivery();
		}
	}

	private void abortDelivery() {
		getCurrentPosition().setX(0);
		getCurrentPosition().setY(0);
		pointingTo = CardinalDirection.NORTH;
		throw new RuntimeException("Drone has disappeared...");
		// TODO
		// Abort all the pending requests
		// Report that in the txt file
	}

	private boolean isValidMovement() {
		int x = getCurrentPosition().getX();
		int y = getCurrentPosition().getY();

		if ((x <= maxPointX && x >= minPointX) && (y <= maxPointY && x >= minPointY)) {
			return true;
		}

		return false;
	}

	private void rotateNinetyDegreesLeft() {
		switch (pointingTo) {
		case NORTH:
			pointingTo = CardinalDirection.WEST;
			break;
		case WEST:
			pointingTo = CardinalDirection.SOUTH;
			break;
		case SOUTH:
			pointingTo = CardinalDirection.EAST;
			break;
		default:
			pointingTo = CardinalDirection.NORTH;
		}
	}

	private void rotateNinetyDegreesRight() {
		switch (pointingTo) {
		case NORTH:
			pointingTo = CardinalDirection.EAST;
			break;
		case EAST:
			pointingTo = CardinalDirection.SOUTH;
			break;
		case SOUTH:
			pointingTo = CardinalDirection.WEST;
			break;
		default:
			pointingTo = CardinalDirection.NORTH;
		}
	}

	public void startRequestsDelivery() {
		for (String request : droneRequests) {
			String[] instructions = request.split("");
			for (String instruction : instructions) {

				try {
					Movement movement = Movement.valueOf(instruction);
					switch (movement) {
					case D:
						rotateNinetyDegreesRight();
						break;
					case I:
						rotateNinetyDegreesLeft();
						break;
					default:
						moveForward();
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("");
					break;
				}
			}
			
			deliveryInformation.add("Request delivered at: " + getCurrentPosition() + ", Poiting to " + pointingTo.getName());
		}
	}

	private void registerDelivery() {
		// TODO Auto-generated method stub
	}

	public List<String> getRequests() {
		return droneRequests;
	}

	public CardinalDirection getPointingTo() {
		return pointingTo;
	}

	public Coordinate getStartingPointCoordinate() {
		return startingPosition;
	}

	public Coordinate getStartingPosition() {
		return startingPosition;
	}

	public Coordinate getCurrentPosition() {
		return currentPosition;
	}

	public List<String> getDeliveryInformation() {
		return deliveryInformation;
	}
}
