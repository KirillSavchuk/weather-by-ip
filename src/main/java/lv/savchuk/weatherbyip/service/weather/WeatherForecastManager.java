package lv.savchuk.weatherbyip.service.weather;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.repository.WeatherForecastRepository;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherForecastManager {

	private final WeatherForecastRepository weatherForecastRepository;
	private final Set<WeatherForecastService> services;

	public WeatherForecast getWeatherForecast(IpCoordinates ipCoordinates) throws NotFoundException {
		WeatherForecast weatherForecast;

		final Geolocation geolocation = ipCoordinates.getGeolocation();
		Optional<WeatherForecast> optWeatherForecast = weatherForecastRepository.findFirstByGeolocationIdBeforeCreatedOn(geolocation);
		if (optWeatherForecast.isPresent()) {
			weatherForecast = optWeatherForecast.get();
		} else {
			weatherForecast = getWeatherForecastFromClient(ipCoordinates)
				.orElseThrow(() -> new NotFoundException(format("Weather Forecast was not found for IP: %s.", ipCoordinates.getIpAddress())));
		}

		weatherForecast.setGeolocation(geolocation);
		weatherForecastRepository.save(weatherForecast);

		return weatherForecast;
	}

	public Optional<WeatherForecast> getWeatherForecastFromClient(IpCoordinates ipCoordinates) {
		for (WeatherForecastService service : services) {
			try {
				return Optional.of(service.getWeatherForecast(ipCoordinates));
			} catch (ExternalClientException ex) {
				log.error("Service '{}' failed to get get geolocation by IP: {}.",
					service.getClass().getSimpleName(), ipCoordinates, ex);
			}
		}
		return Optional.empty();
	}

}