package lv.savchuk.weatherbyip.controller;

import javassist.NotFoundException;
import lv.savchuk.weatherbyip.model.dto.ApiError;
import lv.savchuk.weatherbyip.util.AppMessageUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static lv.savchuk.weatherbyip.util.AppMessageUtil.ErrorType.SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;

public class ControllerExceptionHandlerTest {

	private final String SERVICE_UNAVAILABLE_ERROR_MSG = AppMessageUtil.getError(SERVICE_UNAVAILABLE);

	private final ControllerExceptionHandler exceptionHandler = new ControllerExceptionHandler();

	private NotFoundException exception;
	private WebRequest request;

	@Before
	public void setUp() {
		exception = new NotFoundException("ERROR");
	}

	@Test
	public void handleNotFoundException() {
		final ResponseEntity<ApiError> response = exceptionHandler.handleNotFoundException(exception, request);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);

		final ApiError apiError = response.getBody();
		assertThat(apiError).isNotNull();
		assertThat(apiError.getErrorMessage()).isEqualTo(SERVICE_UNAVAILABLE_ERROR_MSG);
	}

}