package lv.savchuk.weatherbyip.mapper;

import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast;
import org.springframework.stereotype.Service;

import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Requester;
import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Weather;
import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Weather.Temperature;

@Service
public class RequesterWeatherForecastMapper {

	public RequesterWeatherForecast mapFrom(IpCoordinates ipCoordinates, WeatherForecast weatherForecast) {
		final Temperature temperature = Temperature.builder()
			.actual(weatherForecast.getTempActual())
			.min(weatherForecast.getTempMin())
			.max(weatherForecast.getTempMax())
			.build();
		final Weather weather = Weather.builder()
			.title(weatherForecast.getTitle())
			.description(weatherForecast.getDescription())
			.windSpeed(weatherForecast.getWindSpeed())
			.temperature(temperature)
			.build();
		final Requester requester = Requester.builder()
			.ipAddress(ipCoordinates.getIpAddress())
			.latitude(ipCoordinates.getLatitude())
			.longitude(ipCoordinates.getLongitude())
			.country(ipCoordinates.getGeolocation().getCountry())
			.city(ipCoordinates.getGeolocation().getCity())
			.build();
		return RequesterWeatherForecast.builder()
			.requester(requester)
			.weather(weather)
			.build();
	}

}