package lv.savchuk.weatherbyip.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static lv.savchuk.weatherbyip.service.HttpRequestService.HEADER_X_FORWARDED_FOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestServiceTest {

	public static final String IP_ADDRESS = "IP_ADDRESS";

	private final HttpRequestService service = new HttpRequestService();

	@Mock
	private HttpServletRequest request;

	@Test
	public void getIpAddress_success_ipFromRemoteAddress() {
		when(request.getRemoteAddr()).thenReturn(IP_ADDRESS);

		final String ipAddress = service.getIpAddress(request);

		assertThat(ipAddress).isEqualTo(IP_ADDRESS);
	}

	@Test
	public void getIpAddress_success_ipFromHeader() {
		when(request.getHeader(HEADER_X_FORWARDED_FOR)).thenReturn(IP_ADDRESS);

		final String ipAddress = service.getIpAddress(request);

		assertThat(ipAddress).isEqualTo(IP_ADDRESS);
	}

}