package lv.savchuk.weatherbyip.controller;

import javassist.NotFoundException;
import lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast;
import lv.savchuk.weatherbyip.service.HttpRequestService;
import lv.savchuk.weatherbyip.service.WeatherByIpSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastControllerTest {

	public static final String IP_ADDRESS = "IP_ADDRESS";

	@Mock
	private WeatherByIpSearchService weatherByIpSearchService;
	@Mock
	private HttpRequestService requestService;
	@InjectMocks
	private WeatherForecastController controller;

	@Mock
	private HttpServletRequest request;

	private RequesterWeatherForecast response;

	@Before
	public void setUp() throws Exception {
		response = RequesterWeatherForecast.builder().build();

		when(requestService.getIpAddress(request)).thenReturn(IP_ADDRESS);
		when(weatherByIpSearchService.getWeatherForecastByIp(IP_ADDRESS)).thenReturn(response);
	}

	@Test
	public void getWeatherForecast_success() throws NotFoundException {
		final ResponseEntity<RequesterWeatherForecast> weatherForecast = controller.getWeatherForecast(request);

		assertThat(weatherForecast.getBody()).isEqualTo(response);
		assertThat(weatherForecast.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}