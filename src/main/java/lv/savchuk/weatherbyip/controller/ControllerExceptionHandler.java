package lv.savchuk.weatherbyip.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.dto.ApiError;
import lv.savchuk.weatherbyip.util.AppMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static lv.savchuk.weatherbyip.util.AppMessageUtil.ErrorType.SERVICE_UNAVAILABLE;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private final String SERVICE_UNAVAILABLE_ERROR_MSG = AppMessageUtil.getError(SERVICE_UNAVAILABLE);

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex, WebRequest request) {
		log.error("Failed to get weather forecast by IP address. Error: {}.", ex.getMessage());
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
			.body(new ApiError(SERVICE_UNAVAILABLE_ERROR_MSG));
	}

}