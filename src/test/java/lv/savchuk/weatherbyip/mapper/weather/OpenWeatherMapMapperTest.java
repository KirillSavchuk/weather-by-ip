package lv.savchuk.weatherbyip.mapper.weather;

import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapResource;
import lv.savchuk.weatherbyip.model.WeatherForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lv.savchuk.weatherbyip.model.WeatherForecast.Temperature;
import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherMapMapperTest {

	private OpenWeatherMapMapper mapper;

	private final String WEATHER_TITLE = "WEATHER_TITLE";
	private final String WEATHER_DESCRIPTION = "WEATHER_DESCRIPTION";
	private final Float WIND_SPEED = 200f;
	private final Float TEMPERATURE_ACTUAL = 50f;
	private final Float TEMPERATURE_MAX = 60f;
	private final Float TEMPERATURE_MIN = 40f;

	@BeforeEach
	void setUp() {
		mapper = new OpenWeatherMapMapper();
	}

	@Test
	void mapFrom_success() {
		final OpenWeatherMapResource resource = OpenWeatherMapResource.builder()
			.wind(OpenWeatherMapResource.Wind.builder()
				.speed(WIND_SPEED)
				.build())
			.temperature(OpenWeatherMapResource.Temperature.builder()
				.actual(TEMPERATURE_ACTUAL)
				.max(TEMPERATURE_MAX)
				.min(TEMPERATURE_MIN)
				.build())
			.weather(List.of(OpenWeatherMapResource.Weather.builder()
				.main(WEATHER_TITLE)
				.description(WEATHER_DESCRIPTION)
				.build()))
			.build();

		final WeatherForecast weatherForecast = mapper.mapFrom(resource);
		final Temperature temperature = weatherForecast.getTemperature();

		assertThat(weatherForecast.getWeather()).isEqualTo(WEATHER_TITLE);
		assertThat(weatherForecast.getDescription()).isEqualTo(WEATHER_DESCRIPTION);
		assertThat(weatherForecast.getWindSpeed()).isEqualTo(WIND_SPEED);
		assertThat(temperature.getActual()).isEqualTo(TEMPERATURE_ACTUAL);
		assertThat(temperature.getMax()).isEqualTo(TEMPERATURE_MAX);
		assertThat(temperature.getMin()).isEqualTo(TEMPERATURE_MIN);
	}

}