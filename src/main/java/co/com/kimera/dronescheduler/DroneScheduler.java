package co.com.kimera.dronescheduler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import co.com.kimera.dronescheduler.drone.Drone;
import co.com.kimera.dronescheduler.drone.impl.CardinalDirection;
import co.com.kimera.dronescheduler.drone.impl.Coordinate;
import co.com.kimera.dronescheduler.drone.impl.SuCorrientazoDrone;
import co.com.kimera.dronescheduler.util.FileUtil;

/**
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class DroneScheduler
 * @date May 10, 2020
 */
public class DroneScheduler {
	
	public static void main(String[] args) {
		
		String request1 = "AAAAIAAAAAAAA";
		String request2 = "DDDAIAD";
		String request3 = "AAIADAD";
		
		Drone d1 = new SuCorrientazoDrone(CardinalDirection.NORTH, new Coordinate(5,5), 3, 20);
		d1.addInstructionsRequest(request1);
		d1.addInstructionsRequest(request2);
		d1.addInstructionsRequest(request3);
		d1.startRequestsDelivery();
		
		Drone d2 = new SuCorrientazoDrone();
		
		String file1 = "test-s4n.txt";
		try {
			List<String> lines = FileUtil.readFile(file1);
			
			for(String request : lines) {
				d2.addInstructionsRequest(request);
			}
			
			d2.startRequestsDelivery();
			FileUtil.writeFile(d2.getInformationDeliveries(), "/Users/jlondono/d1Output.txt");
			FileUtil.writeFile(d1.getInformationDeliveries(), "/Users/jlondono/d1Output-2.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
