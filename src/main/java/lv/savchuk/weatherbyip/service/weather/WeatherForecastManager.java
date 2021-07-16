package lv.savchuk.weatherbyip.service.weather;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherForecastManager {

	private final WeatherForecastDatabaseManager databaseManager;
	private final WeatherForecastExternalClientManager clientManager;

	public WeatherForecast getWeatherForecast(IpCoordinates ipCoordinates) throws NotFoundException {
		final Geolocation geolocation = ipCoordinates.getGeolocation();

		final WeatherForecast weatherForecast = databaseManager.getNotExpired(geolocation)
			.orElse(clientManager.getCoordinatesByIp(ipCoordinates));

		weatherForecast.setGeolocation(geolocation);
		return databaseManager.persist(weatherForecast);
	}


}