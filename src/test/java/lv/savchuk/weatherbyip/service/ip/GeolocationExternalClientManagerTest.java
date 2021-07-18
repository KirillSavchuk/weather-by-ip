package lv.savchuk.weatherbyip.service.ip;

import javassist.NotFoundException;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
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
public class GeolocationExternalClientManagerTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public static final String IP_ADDRESS = "IP_ADDRESS";

	@Mock
	private ExternalClientIpApiService ipApiService;
	@Mock
	private ExternalClientIpStackService ipStackService;

	private GeolocationExternalClientManager manager;

	private IpCoordinates ipCoordinates;

	@Before
	public void setUp() throws ExternalClientException {
		manager = new GeolocationExternalClientManager(Set.of(ipApiService, ipStackService));

		ipCoordinates = IpCoordinates.builder().ipAddress(IP_ADDRESS).build();

		when(ipApiService.getFailureRecoveryTime()).thenReturn(LocalTime.MAX);
		when(ipStackService.getFailureRecoveryTime()).thenReturn(LocalTime.MAX);

		when(ipApiService.getCoordinates(IP_ADDRESS)).thenReturn(ipCoordinates);
		when(ipStackService.getCoordinates(IP_ADDRESS)).thenReturn(ipCoordinates);
	}

	@Test
	public void getCoordinatesByIp_success_firstService() throws NotFoundException {
		final IpCoordinates actualIpCoordinates = manager.getCoordinates(IP_ADDRESS);

		assertThat(actualIpCoordinates).isEqualTo(ipCoordinates);
	}

	@Test
	public void getCoordinatesByIp_success_secondService() throws ExternalClientException, NotFoundException {
		when(ipApiService.getCoordinates(IP_ADDRESS))
			.thenThrow(new ExternalClientException("ERROR", "RESPONSE"));

		final IpCoordinates actualIpCoordinates = manager.getCoordinates(IP_ADDRESS);

		assertThat(actualIpCoordinates).isEqualTo(ipCoordinates);
	}

	@Test
	public void getCoordinatesByIp_fail_servicesNotValid() throws NotFoundException, ExternalClientException {
		exception.expect(NotFoundException.class);
		exception.expectMessage(format("Geolocation data for IP '%s' was not found!", IP_ADDRESS));

		when(ipApiService.getCoordinates(IP_ADDRESS))
			.thenThrow(new ExternalClientException("ERROR", "RESPONSE"));
		when(ipStackService.getCoordinates(IP_ADDRESS))
			.thenThrow(new ExternalClientException("ERROR", "RESPONSE"));

		manager.getCoordinates(IP_ADDRESS);
	}

}