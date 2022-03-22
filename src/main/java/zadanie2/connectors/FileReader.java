package zadanie2.connectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class FileReader {

	private String filePath;

	public FileReader(String filePath) {
		super();
		this.filePath = filePath;
	}

	public String getStringFromFile() throws IOException {
		InputStream inputStream = new FileInputStream(new File(filePath));

		String fileString = createStringFromStream(inputStream);

		inputStream.close();
		return fileString;
	}

	private String createStringFromStream(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining());
	}
}
