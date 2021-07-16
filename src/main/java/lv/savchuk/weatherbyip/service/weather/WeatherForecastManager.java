package lv.savchuk.weatherbyip.service.weather;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.repository.WeatherForecastRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherForecastManager {

	private final WeatherForecastRepository weatherForecastRepository;
	private final WeatherForecastClientManager clientManager;

	public WeatherForecast getWeatherForecast(IpCoordinates ipCoordinates) throws NotFoundException {
		final Geolocation geolocation = ipCoordinates.getGeolocation();

		final WeatherForecast weatherForecast = weatherForecastRepository.findOneBeforeCreatedOn(geolocation)
			.orElse(clientManager.getCoordinatesByIp(ipCoordinates));

		weatherForecast.setGeolocation(geolocation);
		weatherForecastRepository.save(weatherForecast);

		return weatherForecast;
	}


}