package lv.savchuk.weatherbyip.service.weather;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.config.AppCacheConfig;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherForecastManager {

	private final WeatherForecastDatabaseManager databaseManager;
	private final WeatherForecastExternalClientManager clientManager;

	@Cacheable(value = AppCacheConfig.CACHE_GET_WEATHER_FORECAST_BY_IP_COORDINATES)
	public WeatherForecast getWeatherForecast(IpCoordinates ipCoordinates) throws NotFoundException {
		final Geolocation geolocation = ipCoordinates.getGeolocation();

		final WeatherForecast weatherForecast = databaseManager.getNotExpired(geolocation)
			.orElse(clientManager.getWeatherForecast(ipCoordinates));

		weatherForecast.setGeolocation(geolocation);
		return databaseManager.persist(weatherForecast);
	}


}