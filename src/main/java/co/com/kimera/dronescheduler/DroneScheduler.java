package co.com.kimera.dronescheduler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

		String file1 = "/Users/jlondono/in01.txt";
		String file2 = "/Users/jlondono/in02.txt";
		String file3 = "/Users/jlondono/in03.txt";
		
		try {
			processInstructionFromFile(file1);
			processInstructionFromFile(file2);
			processInstructionFromFile(file3);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Process the instructions from the file and dispatch the drone
	 * @param filePath
	 * @throws IOException
	 */
	private static void processInstructionFromFile(String filePath) throws IOException {
		
		Drone myDrone = new SuCorrientazoDrone(CardinalDirection.NORTH, new Coordinate(0, 0), 3, 10);
		Path pathFile = Paths.get(filePath);
		
		Charset charset = Charset.forName("ISO-8859-1");
		List<String> lines = Files.readAllLines(pathFile, charset);
		lines.stream().forEach(line -> myDrone.addInstructionsRequest(line));
		myDrone.startRequestsDelivery();
		
		String fileName = pathFile.toFile().getName();
		String codeFileNameIn = fileName.substring(fileName.indexOf("in") + 2, fileName.length());
		String fileOut =  String.format("%s/out%s", pathFile.getParent(), codeFileNameIn);
		FileUtil.writeFile(myDrone.getInformationDeliveries(), fileOut);
	}
}
