package co.com.kimera.dronescheduler.drone;

import co.com.kimera.dronescheduler.drone.impl.CardinalDirection;
import co.com.kimera.dronescheduler.drone.impl.Coordinate;

/**
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class Drone
 * @date May 10, 2020
 */
public interface Drone {
	
	/**
	 * Add the information for a request
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @param request
	 */
	public void addInstructionsRequest(String request);
	
	/**
	 * Start the drone to deliver all the requests
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 */
	public void startRequestsDelivery();
	
	/**
	 * Return all the information about a set of delivers
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @return String
	 */
	public String getInformationDeliveries();
	
	/**
	 * Deliver the current coordinate from the drone
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @return
	 */
	public Coordinate getCurrentPosition();
	
	/**
	 * Deliver the current cardinal direction which is being pointed by the the drone
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @return CardinalDirection
	 */
	public CardinalDirection getPointingTo();
}
