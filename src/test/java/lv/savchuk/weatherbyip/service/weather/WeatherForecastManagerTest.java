package lv.savchuk.weatherbyip.service.weather;

import javassist.NotFoundException;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastManagerTest {

	@Mock
	private WeatherForecastDatabaseManager databaseManager;
	@Mock
	private WeatherForecastExternalClientManager clientManager;
	@InjectMocks
	private WeatherForecastManager manager;

	private Geolocation geolocation;
	private IpCoordinates ipCoordinates;
	private WeatherForecast weatherForecast;

	@Before
	public void setUp() {
		geolocation = Geolocation.builder().build();
		ipCoordinates = IpCoordinates.builder().geolocation(geolocation).build();
		weatherForecast = WeatherForecast.builder().build();

		when(databaseManager.persist(weatherForecast)).thenReturn(weatherForecast);
	}

	@Test
	public void getWeatherForecast_success_getFromDB() throws NotFoundException {
		when(databaseManager.getNotExpired(geolocation)).thenReturn(Optional.of(weatherForecast));

		final WeatherForecast actualWeatherForecast = manager.getWeatherForecast(ipCoordinates);

		assertThat(actualWeatherForecast).isEqualTo(weatherForecast);
		assertThat(actualWeatherForecast.getGeolocation()).isEqualTo(geolocation);
		verify(databaseManager).persist(weatherForecast);
	}

	@Test
	public void getWeatherForecast_success_getFromClient() throws NotFoundException {
		when(databaseManager.getNotExpired(geolocation)).thenReturn(Optional.empty());
		when(clientManager.getWeatherForecast(ipCoordinates)).thenReturn(weatherForecast);

		final WeatherForecast actualWeatherForecast = manager.getWeatherForecast(ipCoordinates);

		assertThat(actualWeatherForecast).isEqualTo(weatherForecast);
		assertThat(actualWeatherForecast.getGeolocation()).isEqualTo(geolocation);
		verify(databaseManager).persist(weatherForecast);
	}

	@Test(expected = NotFoundException.class)
	public void getWeatherForecast_fail_exceptionIsThrownByClient() throws NotFoundException {
		when(databaseManager.getNotExpired(geolocation)).thenReturn(Optional.empty());
		when(clientManager.getWeatherForecast(ipCoordinates)).thenThrow(new NotFoundException("ERROR"));

		manager.getWeatherForecast(ipCoordinates);

		verify(databaseManager, never()).persist(weatherForecast);
	}

}