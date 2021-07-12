package lv.savchuk.weatherbyip.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.WeatherForecast;
import lv.savchuk.weatherbyip.service.WeatherByIpSearchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherForecastController {

	private final WeatherByIpSearchService service;

	@GetMapping(path = "/weather")
	@ApiOperation(
		value = "Get current weather forecast",
		notes = "Returns current weather forecast in the requester location determined by requester IP address.",
		response = WeatherForecast.class)
	public ResponseEntity<WeatherForecast> getWeatherForecast(HttpServletRequest request) {
		final String ipAddress = request.getRemoteAddr(); //TODO: What if gateway?
		final WeatherForecast weatherForecast = service.getWeatherForecastByIp(ipAddress);
		return ResponseEntity.ok().body(weatherForecast);
	}

}