package lv.savchuk.weatherbyip.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.model.WeatherForecast;
import lv.savchuk.weatherbyip.service.WeatherByIpSearchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
		final String ipAddress = "85.234.174.19"; // getIpAddress(request);
		final WeatherForecast weatherForecast = service.getWeatherForecastByIp(ipAddress);
		return ResponseEntity.ok().body(weatherForecast);
	}

	private String getIpAddress(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader("X-Forwarded-For"))
			.orElse(request.getRemoteAddr());
	}

}