package co.com.kimera.dronescheduler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class to perform different activities with files
 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
 * @project drone-scheduler
 * @class FileUtil
 * @date May 10, 2020
 *
 */
public class FileUtil {
	
	/**
	 * Write a files using the information from a string
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @param lines
	 * @param fileName
	 */
	public static void writeFile(String lines, String fileName) {
		File file = new File(fileName);
		FileWriter fr = null;
		BufferedWriter br = null;
		String header = "===== Reporte de Entregas =====\n";
		lines = header + lines;
		try {
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);
			br.write(lines);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
