package lv.savchuk.weatherbyip.service.weather;

import javassist.NotFoundException;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.Set;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastExternalClientManagerTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public static final String IP_ADDRESS = "IP_ADDRESS";

	@Mock
	private ExternalClientOpenWeatherMapService openWeatherMapService;

	private WeatherForecastExternalClientManager manager;

	private IpCoordinates ipCoordinates;
	private WeatherForecast weatherForecast;

	@Before
	public void setUp() {
		manager = new WeatherForecastExternalClientManager(Set.of(openWeatherMapService));

		ipCoordinates = IpCoordinates.builder().ipAddress(IP_ADDRESS).build();
		weatherForecast = WeatherForecast.builder().build();

		when(openWeatherMapService.getFailureRecoveryTime()).thenReturn(LocalTime.MAX);
	}

	@Test
	public void ggetWeatherForecast_success() throws ExternalClientException, NotFoundException {
		when(openWeatherMapService.getWeatherForecast(ipCoordinates)).thenReturn(weatherForecast);

		final WeatherForecast actualWeatherForecast = manager.getWeatherForecast(ipCoordinates);

		assertThat(actualWeatherForecast).isEqualTo(weatherForecast);
	}

	@Test
	public void getWeatherForecast_fail_serviceNotValid() throws ExternalClientException, NotFoundException {
		exception.expect(NotFoundException.class);
		exception.expectMessage(format("Weather Forecast was not found for IP: %s.", IP_ADDRESS));

		when(openWeatherMapService.getWeatherForecast(ipCoordinates))
			.thenThrow(new ExternalClientException("ERROR", "RESPONSE"));

		manager.getWeatherForecast(ipCoordinates);
	}

}