package lv.savchuk.weatherbyip.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast;
import lv.savchuk.weatherbyip.service.HttpRequestService;
import lv.savchuk.weatherbyip.service.WeatherByIpSearchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static lv.savchuk.weatherbyip.service.HttpRequestService.HEADER_X_FORWARDED_FOR;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherForecastController {

	private final WeatherByIpSearchService weatherByIpSearchService;
	private final HttpRequestService requestService;

	@GetMapping(path = "/weather")
	@ApiOperation(
		value = "Get current weather forecast",
		notes = "Returns current weather forecast in the requester location determined by requester IP address.",
		response = RequesterWeatherForecast.class)
	@ApiImplicitParam(name = HEADER_X_FORWARDED_FOR, value = HEADER_X_FORWARDED_FOR, paramType = "header", dataTypeClass = String.class, example = "80.232.255.188")
	public ResponseEntity<RequesterWeatherForecast> getWeatherForecast(HttpServletRequest request) throws NotFoundException {
		final String ipAddress = requestService.getIpAddress(request);
		final RequesterWeatherForecast weatherForecast = weatherByIpSearchService.getWeatherForecastByIp(ipAddress);
		return ResponseEntity.ok().body(weatherForecast);
	}

}