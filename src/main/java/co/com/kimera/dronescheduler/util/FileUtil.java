package co.com.kimera.dronescheduler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
	 * Write a file using the information from a List of strings
	 * @author <a href="mailto:javier.londonno@gmail.com">Javier Londoño</a> <br>
	 * @date May 10, 2020
	 * @param fileName
	 * @return List<String>
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static List<String> readFile(String fileName) throws IOException, URISyntaxException {
		Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
		List<String> lines = null;
		Charset charset = Charset.forName("ISO-8859-1");
		lines = Files.readAllLines(path, charset);
		return lines;
	}
	
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
