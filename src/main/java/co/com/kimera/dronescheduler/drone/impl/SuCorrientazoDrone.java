package co.com.kimera.dronescheduler.drone.impl;

import java.util.ArrayList;
import java.util.List;

import co.com.kimera.dronescheduler.drone.Drone;
import co.com.kimera.dronescheduler.exception.DroneExceedsRequestsLimitException;
import co.com.kimera.dronescheduler.exception.IlegalDroneInstructionException;
import co.com.kimera.dronescheduler.exception.OutOfReachDroneException;

/**
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class SuCorrientazoDrone
 * @date May 10, 2020
 */
public class SuCorrientazoDrone implements Drone {

	private static final int MAX_NUMBER_REQUESTS_PER_TRIP = 3;
	private static final int MAX_NUMBER_BLOCKS_TO_MOVE = 10;

	private List<String> droneRequests = new ArrayList<>();
	private List<String> deliveryInformation = new ArrayList<>();
	private CardinalDirection pointingTo;
	private CardinalDirection startingPointingTo;
	private Coordinate currentPosition;
	private Coordinate startingPosition;
	private Integer requestsLimitPerDron;
	private Integer blocksToMoveAroundLimit;

	public SuCorrientazoDrone() {
		this.pointingTo = CardinalDirection.NORTH;
		this.startingPointingTo = CardinalDirection.NORTH;
		this.currentPosition = new Coordinate(0, 0);
		this.startingPosition = new Coordinate(0, 0);
	}

	public SuCorrientazoDrone(CardinalDirection pointingTo, Coordinate startingPosition, Integer requestsLimitPerDron,
			Integer blocksToMoveAroundLimit) {
		this.pointingTo = pointingTo;
		this.currentPosition = new Coordinate(startingPosition.getX(), startingPosition.getY());

		this.startingPointingTo = pointingTo;
		this.startingPosition = startingPosition;

		this.requestsLimitPerDron = requestsLimitPerDron;
		this.blocksToMoveAroundLimit = blocksToMoveAroundLimit;
	}

	public void addInstructionsRequest(String request) {
		int limit = requestsLimitPerDron != null ? requestsLimitPerDron : MAX_NUMBER_REQUESTS_PER_TRIP;
		if (droneRequests.size() >= limit) {
			throw new DroneExceedsRequestsLimitException("Drone is not able to load another request");
		}
		droneRequests.add(request);
	}

	/**
	 * Move a position the drone
	 * 
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 */
	private void moveForward() {
		int xToMove = getCurrentPosition().getX();
		int yToMove = getCurrentPosition().getY();
		switch (pointingTo) {
		case NORTH:
			yToMove++;
			break;
		case EAST:
			xToMove++;
			break;
		case SOUTH:
			yToMove--;
			break;
		default:
			xToMove--;
		}

		if (isValidMovement(xToMove, yToMove)) {
			getCurrentPosition().setX(xToMove);
			getCurrentPosition().setY(yToMove);
		} else {
			throw new OutOfReachDroneException(
					"Drone is out of reach so it will go back to the starting position and will suspend all the deliveries");
		}
	}

	/**
	 * Determine if a movement from the drone is valid (the movement is inside of
	 * the valid area)
	 * 
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @param newX
	 * @param newY
	 * @return boolean
	 */
	private boolean isValidMovement(int newX, int newY) {

		// Calculate the distance between the starting and the current coordinate
		double distanceBetweenTwoPoints = Math.sqrt(
				Math.pow(newX - getStartingPosition().getX(), 2) + Math.pow((newY - getStartingPosition().getY()), 2));
		int definedLimit = blocksToMoveAroundLimit != null ? blocksToMoveAroundLimit : MAX_NUMBER_BLOCKS_TO_MOVE;

		if (distanceBetweenTwoPoints <= definedLimit) {
			return true;
		}
		return false;
	}

	/**
	 * Rotate the drone ninety degrees to the left side
	 * 
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 */
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

	/**
	 * Rotate the drone ninety degrees to the right side
	 * 
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 */
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
		try {
			for (String request : droneRequests) {
				String[] instructions = request.split("");

				try {
					for (String instruction : instructions) {

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
					}

					registerActivity(String.format("%s, direccion %s \n", getCurrentPosition(),
							pointingTo.getName()));

				} catch (IllegalArgumentException e) {
					throw new IlegalDroneInstructionException(String.format(
							"An instruction registered by the user is invalid (valid ones are A, D and I).  User input: %s",
							request));
				}
			}
		} catch (OutOfReachDroneException e) {
			returnDroneToStartingPosition();
			registerActivity(String.format("%s, direccion %s. %s \n",
					getCurrentPosition(), pointingTo.getName(), e.getMessage()));
		}
	}

	/**
	 * Return the drone to the starting position
	 * 
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 */
	private void returnDroneToStartingPosition() {
		getCurrentPosition().setX(getStartingPosition().getX());
		getCurrentPosition().setY(getStartingPosition().getY());
		setPointingTo(getStartingPointingTo());
	}

	/**
	 * Record the information for every activity of the drone
	 * 
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @param information
	 */
	private void registerActivity(String information) {
		deliveryInformation.add(information);
	}

	public String getInformationDeliveries() {
		StringBuilder information = new StringBuilder();
		deliveryInformation.forEach(information :: append);
		return information.toString();
	}

	public List<String> getRequests() {
		return droneRequests;
	}

	public CardinalDirection getPointingTo() {
		return pointingTo;
	}

	private void setPointingTo(CardinalDirection pointingTo) {
		this.pointingTo = pointingTo;
	}

	public Coordinate getCurrentPosition() {
		return currentPosition;
	}

	public Coordinate getStartingPosition() {
		return startingPosition;
	}

	public List<String> getDeliveryInformation() {
		return deliveryInformation;
	}

	private CardinalDirection getStartingPointingTo() {
		return startingPointingTo;
	}
}
