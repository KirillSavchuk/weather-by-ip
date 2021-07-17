package lv.savchuk.weatherbyip.service.weather;

import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.repository.WeatherForecastRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastDatabaseManagerTest {

	@Mock
	private WeatherForecastRepository repository;
	@InjectMocks
	private WeatherForecastDatabaseManager manager;

	private WeatherForecast weatherForecast;

	@Before
	public void setUp() {
		weatherForecast = WeatherForecast.builder().build();
	}

	@Test
	public void persist_success() {
		manager.persist(weatherForecast);

		verify(repository).save(weatherForecast);
	}

	@Test
	public void getNotExpired_success() {
		final Geolocation geolocation = Geolocation.builder().build();
		when(repository.findOneBeforeByGeolocation(eq(geolocation), any(Timestamp.class)))
			.thenReturn(Optional.of(weatherForecast));

		final Optional<WeatherForecast> optWeatherForecast = manager.getNotExpired(geolocation);

		assertThat(optWeatherForecast).isPresent();
		assertThat(optWeatherForecast.get()).isEqualTo(weatherForecast);
		verify(repository).findOneBeforeByGeolocation(eq(geolocation), any(Timestamp.class));
	}

}