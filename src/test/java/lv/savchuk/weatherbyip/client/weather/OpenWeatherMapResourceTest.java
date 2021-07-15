package lv.savchuk.weatherbyip.client.weather;

import lv.savchuk.weatherbyip.client.ResourceMapperTester;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenWeatherMapResourceTest extends ResourceMapperTester {

	public String getFileName() {
		return "open-weather-response-success.json";
	}

	@Test
	public void testMapping() throws IOException {
		final OpenWeatherMapResource resource = getResource(OpenWeatherMapResource.class);

		final OpenWeatherMapResource.Coordinates coordinates = resource.getCoordinates();
		assertThat(coordinates.getLongitude()).isEqualTo(24.1f);
		assertThat(coordinates.getLatitude()).isEqualTo(56.95f);

		final OpenWeatherMapResource.Temperature temperature = resource.getTemperature();
		assertThat(temperature.getActual()).isEqualTo(301.12f);
		assertThat(temperature.getFeelsLike()).isEqualTo(302.24f);
		assertThat(temperature.getMax()).isEqualTo(301.17f);
		assertThat(temperature.getMin()).isEqualTo(299.75f);
		assertThat(temperature.getPressure()).isEqualTo(1016f);
		assertThat(temperature.getHumidity()).isEqualTo(57f);

		final OpenWeatherMapResource.Weather weather = resource.getWeather().get(0);
		assertThat(weather.getMain()).isEqualTo("Clear");
		assertThat(weather.getDescription()).isEqualTo("clear sky");

		final OpenWeatherMapResource.Wind wind = resource.getWind();
		assertThat(wind.getDegrees()).isEqualTo(10);
		assertThat(wind.getSpeed()).isEqualTo(4.12f);
	}

}