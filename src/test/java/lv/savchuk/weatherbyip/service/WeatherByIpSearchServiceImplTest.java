package lv.savchuk.weatherbyip.service;

import javassist.NotFoundException;
import lv.savchuk.weatherbyip.mapper.RequesterWeatherForecastMapper;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.RequestHistory;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast;
import lv.savchuk.weatherbyip.repository.RequestHistoryRepository;
import lv.savchuk.weatherbyip.service.ip.GeolocationManager;
import lv.savchuk.weatherbyip.service.weather.WeatherForecastManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherByIpSearchServiceImplTest {

	public static final String IP_ADDRESS = "IP_ADDRESS";
	public static final UUID IP_COORDINATES_UUID = UUID.randomUUID();
	public static final UUID WEATHER_FORECAST_UUID = UUID.randomUUID();

	@Mock
	private GeolocationManager ipCoordinatesManager;
	@Mock
	private WeatherForecastManager weatherForecastManager;
	@Mock
	private RequestHistoryRepository requestHistoryRepository;
	@Mock
	private RequesterWeatherForecastMapper responseMapper;
	@InjectMocks
	private WeatherByIpSearchServiceImpl service;

	private RequesterWeatherForecast requesterWeatherForecast;

	@Captor
	private ArgumentCaptor<RequestHistory> requestHistoryCaptor;

	@Before
	public void setUp() throws Exception {
		final IpCoordinates ipCoordinates = IpCoordinates.builder()
			.id(IP_COORDINATES_UUID)
			.build();
		final WeatherForecast weatherForecast = WeatherForecast.builder()
			.id(WEATHER_FORECAST_UUID)
			.build();
		requesterWeatherForecast = RequesterWeatherForecast.builder().build();

		when(ipCoordinatesManager.getCoordinates(IP_ADDRESS)).thenReturn(ipCoordinates);
		when(weatherForecastManager.getWeatherForecast(ipCoordinates)).thenReturn(weatherForecast);
		when(responseMapper.mapFrom(ipCoordinates, weatherForecast)).thenReturn(requesterWeatherForecast);
	}

	@Test
	public void getWeatherForecastByIp_success() throws NotFoundException {
		final RequesterWeatherForecast actualWeatherForecast = service.getWeatherForecastByIp(IP_ADDRESS);

		assertThat(actualWeatherForecast).isEqualTo(requesterWeatherForecast);

		verify(requestHistoryRepository).save(requestHistoryCaptor.capture());
		final RequestHistory requestHistory = requestHistoryCaptor.getValue();
		assertThat(requestHistory.getIpCoordinatesId()).isEqualTo(IP_COORDINATES_UUID);
		assertThat(requestHistory.getWeatherForecastId()).isEqualTo(WEATHER_FORECAST_UUID);
	}

}