package lv.savchuk.weatherbyip.mapper;

import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.UUID;

import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Requester;
import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Weather;
import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Weather.Temperature;
import static org.assertj.core.api.Assertions.assertThat;

public class RequesterWeatherForecastMapperTest {

	private final RequesterWeatherForecastMapper mapper = new RequesterWeatherForecastMapper();

	private IpCoordinates ipCoordinates;
	private WeatherForecast weatherForecast;

	public static final String IP_ADDRESS = "IP_ADDRESS";

	public static final Float LONGITUDE = 1f;
	public static final Float LATITUDE = 2f;
	public static final String COUNTRY = "COUNTRY";
	public static final String CITY = "CITY";

	public static final String WEATHER_TITLE = "WEATHER_TITLE";
	public static final String WEATHER_DESCRIPTION = "WEATHER_DESCRIPTION";
	public static final Float WIND_SPEED = 999f;
	public static final Float TEMP_ACTUAL = 50f;
	public static final Float TEMP_MIN = 40f;
	public static final Float TEMP_MAX = 60f;


	@Before
	public void setUp() {
		final Geolocation geolocation = Geolocation.builder()
			.id(UUID.randomUUID())
			.createdOn(new Date(0))
			.country(COUNTRY)
			.city(CITY)
			.build();
		ipCoordinates = IpCoordinates.builder()
			.id(UUID.randomUUID())
			.createdOn(new Date(0))
			.ipAddress(IP_ADDRESS)
			.longitude(LONGITUDE)
			.latitude(LATITUDE)
			.geolocation(geolocation)
			.build();
		weatherForecast = WeatherForecast.builder()
			.id(UUID.randomUUID())
			.createdOn(new Date(0))
			.title(WEATHER_TITLE)
			.description(WEATHER_DESCRIPTION)
			.windSpeed(WIND_SPEED)
			.tempActual(TEMP_ACTUAL)
			.tempMin(TEMP_MIN)
			.tempMax(TEMP_MAX)
			.build();
	}

	@Test
	public void mapFrom_success() {
		final RequesterWeatherForecast requesterWeatherForecast = mapper.mapFrom(ipCoordinates, weatherForecast);

		final Requester requester = requesterWeatherForecast.getRequester();
		assertThat(requester.getCountry()).isEqualTo(COUNTRY);
		assertThat(requester.getCity()).isEqualTo(CITY);
		assertThat(requester.getLatitude()).isEqualTo(LATITUDE);
		assertThat(requester.getLongitude()).isEqualTo(LONGITUDE);

		final Weather weather = requesterWeatherForecast.getWeather();
		assertThat(weather.getTitle()).isEqualTo(WEATHER_TITLE);
		assertThat(weather.getDescription()).isEqualTo(WEATHER_DESCRIPTION);
		assertThat(weather.getWindSpeed()).isEqualTo(WIND_SPEED);

		final Temperature temperature = weather.getTemperature();
		assertThat(temperature.getActual()).isEqualTo(TEMP_ACTUAL);
		assertThat(temperature.getMax()).isEqualTo(TEMP_MAX);
		assertThat(temperature.getMin()).isEqualTo(TEMP_MIN);
	}

}