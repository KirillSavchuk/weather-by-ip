package lv.savchuk.weatherbyip.service.weather;

import feign.FeignException;
import feign.Request;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapClient;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapResource;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.weather.OpenWeatherMapMapper;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherMapServiceTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public static final Float LATITUDE = 10f;
	public static final Float LONGITUDE = 20f;

	@Mock
	private OpenWeatherMapMapper mapper;
	@Mock
	private OpenWeatherMapClient client;
	@InjectMocks
	private OpenWeatherMapService service;

	private IpCoordinates ipCoordinates;
	private WeatherForecast weatherForecast;

	@Before
	public void setUp() {
		ipCoordinates = IpCoordinates.builder()
			.longitude(LONGITUDE)
			.latitude(LATITUDE)
			.build();
		OpenWeatherMapResource openWeatherMapResource = OpenWeatherMapResource.builder().build();

		when(client.findCurrentWeather(LONGITUDE, LATITUDE)).thenReturn(openWeatherMapResource);
		when(mapper.mapFrom(openWeatherMapResource)).thenReturn(weatherForecast);
	}

	@Test
	public void findCurrentWeather_success() throws ExternalClientException {
		final WeatherForecast weatherForecast = service.getWeatherForecast(ipCoordinates);

		assertThat(weatherForecast).isEqualTo(this.weatherForecast);
	}

	@Test
	public void getCoordinatesByIp_fail_feignExceptionIsThrown() throws ExternalClientException {
		exception.expect(ExternalClientException.class);

		when(client.findCurrentWeather(LONGITUDE, LATITUDE))
			.thenThrow(new FeignException.BadRequest("BadRequest", createDummyRequest(), "ERROR_RESPONSE".getBytes()));

		service.getWeatherForecast(ipCoordinates);

		verifyNoInteractions(mapper.mapFrom(any(OpenWeatherMapResource.class)));
	}

	private Request createDummyRequest() {
		return Request.create(Request.HttpMethod.GET, "URL", Collections.emptyMap(), Request.Body.create("{}"), null);
	}

}