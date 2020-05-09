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

public class FileUtil {

	public static List<String> readFile(String fileName) throws IOException, URISyntaxException {
		Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
		List<String> lines = null;
		Charset charset = Charset.forName("ISO-8859-1");
		lines = Files.readAllLines(path, charset);
		return lines;
	}

	public static void writeFile(List<String> lines, String fileName) {
		File file = new File(fileName);
		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);
			for (String line : lines) {
				br.write(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
