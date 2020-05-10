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

		String request1 = "AAAAIAA";
		String request2 = "DDDAIAD";
		String request3 = "AAIADAD";

		Drone d1 = new SuCorrientazoDrone(CardinalDirection.NORTH, new Coordinate(0, 0), 3, 10);
		d1.addInstructionsRequest(request1);
		d1.addInstructionsRequest(request2);
		d1.addInstructionsRequest(request3);
		d1.startRequestsDelivery();

		try {

			String file1 = "test-s4n.txt";
			Drone d2 = new SuCorrientazoDrone();

			for (String request : getInstructionsFromFile(file1)) {
				d2.addInstructionsRequest(request);
			}
			
			d2.startRequestsDelivery();

			FileUtil.writeFile(d1.getInformationDeliveries(), "/Users/jlondono/d1Output-2.txt");
			FileUtil.writeFile(d2.getInformationDeliveries(), "/Users/jlondono/d1Output.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static List<String> getInstructionsFromFile(String fileName) throws IOException, URISyntaxException {
		return FileUtil.readFile(fileName);
	}
}
