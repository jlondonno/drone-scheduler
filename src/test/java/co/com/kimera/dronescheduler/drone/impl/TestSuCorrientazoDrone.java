package co.com.kimera.dronescheduler.drone.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import co.com.kimera.dronescheduler.drone.Drone;
import co.com.kimera.dronescheduler.exception.DroneExceedsRequestsLimitException;

/**
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class TestSuCorrientazoDrone
 * @date May 10, 2020
 */
public class TestSuCorrientazoDrone {

	private Drone drone1 = new SuCorrientazoDrone();
	private Drone drone2 = new SuCorrientazoDrone(CardinalDirection.NORTH, new Coordinate(0, 0), 3, 10);
	private Drone drone3 = new SuCorrientazoDrone(CardinalDirection.SOUTH, new Coordinate(4, 5), 3, 20);
	private Drone drone4 = new SuCorrientazoDrone(CardinalDirection.WEST, new Coordinate(2, 2), 5, 10);
	private Drone drone5 = new SuCorrientazoDrone(CardinalDirection.WEST, new Coordinate(2, 2), 5, 10);
	private Drone drone6 = new SuCorrientazoDrone(CardinalDirection.NORTH, new Coordinate(0, 0), 3, 5);

	@Before
	public void setUp() {

		String request1 = "AAAAIAA";
		String request2 = "DDDAIAD";
		String request3 = "AAIADAD";
		String request4 = "AAAAAAA";
		String request5 = "AAAAAAA";

		drone1.addInstructionsRequest(request1);
		drone1.addInstructionsRequest(request2);
		drone1.addInstructionsRequest(request3);

		drone2.addInstructionsRequest(request1);
		drone2.addInstructionsRequest(request2);
		drone2.addInstructionsRequest(request3);

		drone3.addInstructionsRequest(request1);
		drone3.addInstructionsRequest(request2);
		drone3.addInstructionsRequest(request3);

		drone4.addInstructionsRequest(request1);
		drone4.addInstructionsRequest(request2);
		drone4.addInstructionsRequest(request3);
		drone4.addInstructionsRequest(request4);
		
		drone5.addInstructionsRequest(request1);
		drone5.addInstructionsRequest(request2);
		drone5.addInstructionsRequest(request3);
		drone5.addInstructionsRequest(request4);
		drone5.addInstructionsRequest(request5);
		
		drone6.addInstructionsRequest(request5);
	}
	
	@Test(expected = DroneExceedsRequestsLimitException.class)
	public void testAddInstructionsRequestExceedLimit() {
		String newRequest = "AAAAADDDAAII";
		drone3.addInstructionsRequest(newRequest);
	}

	@Test
	public void testGetCurrentPositionThreeDeliveriesDefaultPosition() {
		drone1.startRequestsDelivery();
		Coordinate currentCoordinate = drone1.getCurrentPosition();
		CardinalDirection currentCardinalDirection = drone1.getPointingTo();
		Coordinate expectedCoordinate = new Coordinate(0, 0);
		assertEquals("The expected coordinates of the drone is incorrect", expectedCoordinate,
				currentCoordinate);
		assertEquals("The current cardinal direction of the drone is incorrect", CardinalDirection.WEST,
				currentCardinalDirection);
	}

	@Test
	public void testGetCurrentPositionThreeDeliveriesDefinedPosition() {
		drone2.startRequestsDelivery();
		Coordinate currentCoordinate = drone2.getCurrentPosition();
		CardinalDirection currentCardinalDirection = drone2.getPointingTo();
		Coordinate expectedCoordinate = new Coordinate(0, 0);
		assertEquals("The expected coordinates of the drone is incorrect", expectedCoordinate,
				currentCoordinate);
		assertEquals("The current cardinal direction of the drone is incorrect", CardinalDirection.WEST,
				currentCardinalDirection);
	}

	@Test
	public void testGetDeliveryInformationThreeDeliveriesAnotherStarting() {
		drone3.startRequestsDelivery();
		Coordinate currentCoordinate = drone3.getCurrentPosition();
		CardinalDirection currentCardinalDirection = drone3.getPointingTo();
		Coordinate expectedCoordinate = new Coordinate(4, 5);
		assertEquals("The expected coordinates of the drone is incorrect", expectedCoordinate,
				currentCoordinate);
		assertEquals("The current cardinal direction of the drone is incorrect", CardinalDirection.EAST,
				currentCardinalDirection);
	}

	@Test
	public void testGetCurrentPositionFourDeliveriesAnotherStarting() {
		drone4.startRequestsDelivery();
		Coordinate currentCoordinate = drone4.getCurrentPosition();
		CardinalDirection currentCardinalDirection = drone4.getPointingTo();
		Coordinate expectedCoordinate = new Coordinate(2, -5);
		assertEquals("The expected coordinates of the drone is incorrect", expectedCoordinate,
				currentCoordinate);
		assertEquals("The current cardinal direction of the drone is incorrect", CardinalDirection.SOUTH,
				currentCardinalDirection);

	}
	
	@Test
	public void testGetCurrentPositionWhenDroneLeavingArea() {
		drone5.startRequestsDelivery();
		Coordinate currentCoordinate = drone5.getCurrentPosition();
		CardinalDirection currentCardinalDirection = drone5.getPointingTo();
		Coordinate expectedCoordinate = new Coordinate(2, 2);
		assertEquals("The expected coordinates of the drone is incorrect", expectedCoordinate,
				currentCoordinate);
		assertEquals("The current cardinal direction of the drone is incorrect", CardinalDirection.WEST,
				currentCardinalDirection);

	}
	
	@Test
	public void testGetCurrentPositionWhenDroneLeavingAreaFromDefaultPosition() {
		drone6.startRequestsDelivery();
		Coordinate currentCoordinate = drone6.getCurrentPosition();
		CardinalDirection currentCardinalDirection = drone6.getPointingTo();
		Coordinate expectedCoordinate = new Coordinate(0, 0);
		assertEquals("The expected coordinates of the drone is incorrect", expectedCoordinate,
				currentCoordinate);
		assertEquals("The current cardinal direction of the drone is incorrect", CardinalDirection.NORTH,
				currentCardinalDirection);

	}
}
