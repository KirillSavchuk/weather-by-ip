package lv.savchuk.weatherbyip.service.ip;

import feign.FeignException;
import feign.Request;
import lv.savchuk.weatherbyip.client.ip.IpStackClient;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.ip.IpStackMapper;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dto.IpStackResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static lv.savchuk.weatherbyip.model.dto.IpStackResource.Error;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalClientIpStackServiceTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public static final String IP_ADDRESS = "IP_ADDRESS";

	@Mock
	private IpStackClient client;
	@Mock
	private IpStackMapper mapper;
	@InjectMocks
	private ExternalClientIpStackService service;

	private IpCoordinates ipCoordinates;

	@Before
	public void setUp() {
		final IpStackResource ipStackResource = IpStackResource.builder()
			.latitude(1f)
			.longitude(2f)
			.build();
		ipCoordinates = IpCoordinates.builder().build();

		when(client.findGeolocationByIp(eq(IP_ADDRESS), any())).thenReturn(ipStackResource);
		when(mapper.mapFrom(ipStackResource)).thenReturn(ipCoordinates);
	}

	@Test
	public void getCoordinatesByIp_success() throws ExternalClientException {
		final IpCoordinates ipCoordinates = service.getCoordinates(IP_ADDRESS);

		assertThat(ipCoordinates).isEqualTo(this.ipCoordinates);
	}

	@Test
	public void getCoordinatesByIp_fail_feignExceptionIsThrown() throws ExternalClientException {
		exception.expect(ExternalClientException.class);

		when(client.findGeolocationByIp(eq(IP_ADDRESS), any()))
			.thenThrow(new FeignException.BadRequest("BadRequest", createDummyRequest(), null));

		service.getCoordinates(IP_ADDRESS);

		verifyNoInteractions(mapper.mapFrom(any(IpStackResource.class)));
	}

	@Test
	public void getCoordinatesByIp_fail_responseValidationFailed() throws ExternalClientException {
		exception.expect(ExternalClientException.class);

		final IpStackResource ipStackResource = IpStackResource.builder()
			.success(false)
			.error(Error.builder()
				.info("ERROR_INFO")
				.type("ERROR_TYPE")
				.code(500)
				.build())
			.build();
		when(client.findGeolocationByIp(eq(IP_ADDRESS), any())).thenReturn(ipStackResource);

		service.getCoordinates(IP_ADDRESS);

		verifyNoInteractions(mapper.mapFrom(any(IpStackResource.class)));
	}

	@Test
	public void getCoordinatesByIp_fail_noCoordinatesReturned() throws ExternalClientException {
		exception.expect(ExternalClientException.class);

		final IpStackResource ipStackResource = IpStackResource.builder().build();
		when(client.findGeolocationByIp(eq(IP_ADDRESS), any())).thenReturn(ipStackResource);

		service.getCoordinates(IP_ADDRESS);

		verifyNoInteractions(mapper.mapFrom(any(IpStackResource.class)));
	}

	private Request createDummyRequest() {
		return Request.create(Request.HttpMethod.GET, "URL", Collections.emptyMap(), Request.Body.create("{}"), null);
	}

}