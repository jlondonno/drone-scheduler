package co.com.kimera.dronescheduler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import co.com.kimera.dronescheduler.drone.impl.CardinalDirection;
import co.com.kimera.dronescheduler.drone.impl.Coordinate;
import co.com.kimera.dronescheduler.drone.impl.Drone;
import co.com.kimera.dronescheduler.util.FileUtil;

public class DroneScheduler {
	
	public static void main(String[] args) {
		
		String request1 = "AAAAIAA";
		String request2 = "DDDAIAD";
		String request3 = "AAIADAD";
		
		Drone d1 = new Drone(CardinalDirection.NORTH, new Coordinate(0,0), 3, 20);
		d1.addRequest(request1);
		d1.addRequest(request2);
		d1.addRequest(request3);
//		d1.startRequestsDelivery();
		
		Drone d2 = new Drone();
		
		String file1 = "test-s4n.txt";
		try {
			List<String> lines = FileUtil.readFile(file1);
			
			for(String request : lines) {
				d2.addRequest(request);
			}
			
			d2.startRequestsDelivery();
			FileUtil.writeFile(d2.getDeliveryInformation(), "/Users/jlondono/d1Output.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
