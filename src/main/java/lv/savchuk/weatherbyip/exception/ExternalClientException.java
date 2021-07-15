package lv.savchuk.weatherbyip.exception;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.lang.String.format;

@Getter
@AllArgsConstructor
public class ExternalClientException extends Exception {

	private final String errorMessage;
	private final String response;
	private final Throwable initialException;

	public ExternalClientException(String errorMessage, String response) {
		this(errorMessage, response, null);
	}

	public ExternalClientException(FeignException feignException) {
		this(feignException.getMessage(), String.valueOf(feignException.responseBody()), feignException);
	}

	public String toString() {
		return format("External client error: %s. Client response: %s. Initial exception: %s.", errorMessage, response, initialException);
	}

}