package lv.savchuk.weatherbyip.exception;

import feign.FeignException;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;

import static java.lang.String.format;

@AllArgsConstructor
public class ExternalClientException extends Exception {

	private final String errorMessage;
	private final String response;
	private final Throwable initialException;

	public ExternalClientException(String errorMessage, String response) {
		this(errorMessage, response, null);
	}

	public ExternalClientException(FeignException feignException) {
		this(feignException.getMessage(), getResponse(feignException), feignException);
	}

	public String toString() {
		return format("External client error: %s. Client response: %s. Initial exception: %s.", errorMessage, response, initialException);
	}

	private static String getResponse(FeignException feignException) {
		return feignException.responseBody()
			.map(buffer -> StandardCharsets.UTF_8.decode(buffer).toString())
			.orElse(null);
	}

}