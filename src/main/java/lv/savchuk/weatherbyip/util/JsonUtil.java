package lv.savchuk.weatherbyip.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException ex) {
			log.error(format("Failed to parse Object to JSON String. Object: %s.", object.toString()), ex);
		}
		return null;
	}

}
