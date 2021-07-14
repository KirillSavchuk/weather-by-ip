package lv.savchuk.weatherbyip.service.weather;

import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import lv.savchuk.weatherbyip.model.WeatherForecast;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class WeatherForecastManager {

	private final Set<WeatherForecastService> services;

	public WeatherForecastManager(Set<WeatherForecastService> services) {
		this.services = services;
	}

	public Optional<WeatherForecast> getWeatherForecast(IpGeolocation ipGeolocation) {
		WeatherForecast weatherForecast = null;
		for (WeatherForecastService service : services) {
			try {
				weatherForecast = service.getWeatherForecast(ipGeolocation);
			} catch (Exception ex) { //TODO: handle Feign Exception
				log.error("Very bad things happened...", ex);
			}
		}
		return Optional.ofNullable(weatherForecast);
	}

}