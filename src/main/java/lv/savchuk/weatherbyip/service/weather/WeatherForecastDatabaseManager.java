package lv.savchuk.weatherbyip.service.weather;

import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.repository.WeatherForecastRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherForecastDatabaseManager {

	private final WeatherForecastRepository repository;

	public WeatherForecast persist(WeatherForecast weatherForecast) {
		return repository.save(weatherForecast);
	}

	public Optional<WeatherForecast> getNotExpired(Geolocation geolocation) {
		final Timestamp expiredTimestamp = Timestamp.valueOf(LocalDateTime.now().minusHours(1));
		return repository.findOneBeforeByGeolocation(geolocation, expiredTimestamp);
	}

}