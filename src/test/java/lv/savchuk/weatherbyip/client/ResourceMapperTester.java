package lv.savchuk.weatherbyip.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static java.lang.String.format;

public abstract class ResourceMapperTester {

	public abstract String getFileName();

	private final ObjectMapper objectMapper = new ObjectMapper();

	protected <T> T getResource(Class<T> objectCls) throws IOException {
		return objectMapper.readValue(getDataFileContent(), objectCls);
	}

	private String getDataFileContent() throws IOException {
		final String pathToFile = format("data/%s", getFileName());
		final InputStream jsonResource = getClass().getClassLoader().getResourceAsStream(pathToFile);
		return new String(Objects.requireNonNull(jsonResource).readAllBytes());
	}

}