package lv.savchuk.weatherbyip.mapper.weather;

import lv.savchuk.weatherbyip.model.dto.OpenWeatherMapResource;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.springframework.stereotype.Service;

@Service
public class OpenWeatherMapMapper implements WeatherForecastMapper<OpenWeatherMapResource> {

	@Override
	public WeatherForecast mapFrom(OpenWeatherMapResource data) {
		final OpenWeatherMapResource.Temperature dataTemperature = data.getTemperature();
		final OpenWeatherMapResource.Weather dataWeather = data.getWeather().get(0);
		return WeatherForecast.builder()
			.title(dataWeather.getMain())
			.description(dataWeather.getDescription())
			.windSpeed(data.getWind().getSpeed())
			.tempActual(dataTemperature.getActual())
			.tempMin(dataTemperature.getMin())
			.tempMax(dataTemperature.getMax())
			.build();
	}

}