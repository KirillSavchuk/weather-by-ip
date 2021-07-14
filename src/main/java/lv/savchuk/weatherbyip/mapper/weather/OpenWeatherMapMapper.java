package lv.savchuk.weatherbyip.mapper.weather;

import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapResource;
import lv.savchuk.weatherbyip.model.WeatherForecast;
import org.springframework.stereotype.Service;

import java.util.List;

import static lv.savchuk.weatherbyip.model.WeatherForecast.Temperature;

@Service
public class OpenWeatherMapMapper implements WeatherForecastMapper<OpenWeatherMapResource> {

	@Override
	public WeatherForecast mapFrom(OpenWeatherMapResource data) {
		final OpenWeatherMapResource.Temperature dataTemperature = data.getTemperature();
		final Temperature temperature = Temperature.builder()
			.actual(dataTemperature.getActual())
			.max(dataTemperature.getMax())
			.min(dataTemperature.getMin())
			.build();
		final WeatherForecast.WeatherForecastBuilder builder = WeatherForecast.builder()
			.windSpeed(data.getWind().getSpeed())
			.temperature(temperature);
		final List<OpenWeatherMapResource.Weather> weatherList = data.getWeather();
		if (!weatherList.isEmpty()) {
			final OpenWeatherMapResource.Weather weather = weatherList.get(0);
			builder
				.weather(weather.getMain())
				.description(weather.getDescription());
		}
		return builder.build();
	}

}