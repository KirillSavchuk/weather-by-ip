package lv.savchuk.weatherbyip.service.weather;

import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapClient;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapResource;
import lv.savchuk.weatherbyip.mapper.weather.OpenWeatherMapMapper;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import lv.savchuk.weatherbyip.model.Coordinates;
import lv.savchuk.weatherbyip.model.WeatherForecast;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapService implements WeatherForecastService {

	private final OpenWeatherMapClient client;
	private final OpenWeatherMapMapper mapper;

	public WeatherForecast getWeatherForecast(IpGeolocation ipGeolocation) {
		final Coordinates coordinates = ipGeolocation.getCoordinates();
		final OpenWeatherMapResource weatherData = client.findCurrentWeather(coordinates.getLongitude(), coordinates.getLatitude());
		return mapper.mapFrom(weatherData);
	}

}