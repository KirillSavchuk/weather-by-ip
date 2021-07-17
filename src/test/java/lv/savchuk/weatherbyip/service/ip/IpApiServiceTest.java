package lv.savchuk.weatherbyip.service.ip;

import feign.FeignException;
import feign.Request;
import lv.savchuk.weatherbyip.client.ip.IpApiClient;
import lv.savchuk.weatherbyip.model.dto.IpApiResource;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.ip.IpApiMapper;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static feign.Request.Body;
import static feign.Request.HttpMethod;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IpApiServiceTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public static final String IP_ADDRESS = "IP_ADDRESS";

	@Mock
	private IpApiClient client;
	@Mock
	private IpApiMapper mapper;
	@InjectMocks
	private ExternalClientIpApiService service;

	private IpCoordinates ipCoordinates;

	@Before
	public void setUp() {
		final IpApiResource ipApiResource = IpApiResource.builder().build();
		ipCoordinates = IpCoordinates.builder().build();

		when(client.findGeolocationByIp(eq(IP_ADDRESS), any())).thenReturn(ipApiResource);
		when(mapper.mapFrom(ipApiResource)).thenReturn(ipCoordinates);
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
			.thenThrow(new FeignException.BadRequest("BadRequest", createDummyRequest(), "ERROR_RESPONSE".getBytes()));

		service.getCoordinates(IP_ADDRESS);

		verifyNoInteractions(mapper.mapFrom(any(IpApiResource.class)));
	}

	private Request createDummyRequest() {
		return Request.create(HttpMethod.GET, "URL", Collections.emptyMap(), Body.create("{}"), null);
	}

}