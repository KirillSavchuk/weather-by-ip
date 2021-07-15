package lv.savchuk.weatherbyip.mapper.weather;

import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapResource;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static lv.savchuk.weatherbyip.client.weather.OpenWeatherMapResource.*;
import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherMapMapperTest {

	private OpenWeatherMapMapper mapper;

	private final static String WEATHER_TITLE = "WEATHER_TITLE";
	private final static String WEATHER_DESCRIPTION = "WEATHER_DESCRIPTION";
	private final static Float WIND_SPEED = 200f;
	private final static Float TEMPERATURE_ACTUAL = 50f;
	private final static Float TEMPERATURE_MAX = 60f;
	private final static Float TEMPERATURE_MIN = 40f;

	@BeforeEach
	void setUp() {
		mapper = new OpenWeatherMapMapper();
	}

	@Test
	public void mapFrom_success() {
		final OpenWeatherMapResource resource = OpenWeatherMapResource.builder()
			.wind(Wind.builder()
				.speed(WIND_SPEED)
				.build())
			.temperature(Temperature.builder()
				.actual(TEMPERATURE_ACTUAL)
				.max(TEMPERATURE_MAX)
				.min(TEMPERATURE_MIN)
				.build())
			.weather(List.of(Weather.builder()
				.main(WEATHER_TITLE)
				.description(WEATHER_DESCRIPTION)
				.build()))
			.build();

		final WeatherForecast weatherForecast = mapper.mapFrom(resource);

		assertThat(weatherForecast.getTitle()).isEqualTo(WEATHER_TITLE);
		assertThat(weatherForecast.getDescription()).isEqualTo(WEATHER_DESCRIPTION);
		assertThat(weatherForecast.getWindSpeed()).isEqualTo(WIND_SPEED);
		assertThat(weatherForecast.getTempActual()).isEqualTo(TEMPERATURE_ACTUAL);
		assertThat(weatherForecast.getTempMax()).isEqualTo(TEMPERATURE_MAX);
		assertThat(weatherForecast.getTempMin()).isEqualTo(TEMPERATURE_MIN);
	}

}