package co.com.kimera.dronescheduler.drone.impl;

import java.util.ArrayList;
import java.util.List;

import co.com.kimera.dronescheduler.drone.Drone;

/**
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class SuCorrientazoDrone
 * @date May 10, 2020
 */
public class SuCorrientazoDrone implements Drone {

	private static final int MAX_NUMBER_REQUESTS_PER_TRIP = 3;
	private static final int MAX_NUMBER_BLOCKS_TO_MOVE = 10;

	private List<String> droneRequests = new ArrayList<String>();
	private List<String> deliveryInformation = new ArrayList<String>();
	private CardinalDirection pointingTo;
	private Coordinate currentPosition;
	private Coordinate startingPosition;
	private Integer requestsLimitPerDron;
	private Integer blocksToMoveAroundLimit;

	public SuCorrientazoDrone() {
		pointingTo = CardinalDirection.NORTH;
		currentPosition = new Coordinate(0, 0);
		startingPosition = new Coordinate(0, 0);
	}

	public SuCorrientazoDrone(CardinalDirection pointingTo, Coordinate startingPosition, Integer requestsLimitPerDron,
			Integer blocksToMoveAroundLimit) {
		this.pointingTo = pointingTo;
		this.currentPosition = startingPosition;
		this.requestsLimitPerDron = requestsLimitPerDron;
		this.blocksToMoveAroundLimit = blocksToMoveAroundLimit;
	}

	public void addInstructionsRequest(String request) {
		int limit = requestsLimitPerDron != null ? requestsLimitPerDron : MAX_NUMBER_REQUESTS_PER_TRIP;
		if (droneRequests.size() >= limit) {
			throw new RuntimeException("Drone is not able to load another request");
		}
		droneRequests.add(request);
	}
	
	/**
	 * Move a position the drone
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
			abortDelivery();
		}
	}
	
	/**
	 * Abort all the deliveries from the dron when it leaves the valid area and
	 * return the drone to the starting point
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 */
	private void abortDelivery() {
		getCurrentPosition().setX(getStartingPosition().getX());
		getCurrentPosition().setY(getStartingPosition().getY());
		pointingTo = CardinalDirection.NORTH;
		throw new RuntimeException("Drone has disappeared...");
		// TODO
		// Abort all the pending requests
		// Report that in the txt file
	}
	
	
	/**
	 * Determine if a movement from the drone is valid (the movement is inside of the valid area)
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @param newX
	 * @param newY
	 * @return boolean
	 */
	private boolean isValidMovement(int newX, int newY) {
		
		//Calculate the distance between the starting and the current position
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

			registerDelivery(String.format("Request delivered at: %s, Poiting to %s", getCurrentPosition(),
					pointingTo.getName()));
		}
	}
	
	/**
	 * Record the information for every delivery
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @param information
	 */
	private void registerDelivery(String information) {
		deliveryInformation.add(information);
	}
	
	public String getInformationDeliveries(){
		return "";
	}

	public List<String> getRequests() {
		return droneRequests;
	}

	public CardinalDirection getPointingTo() {
		return pointingTo;
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
}
