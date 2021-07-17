package lv.savchuk.weatherbyip.service.ip;

import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.repository.GeolocationRepository;
import lv.savchuk.weatherbyip.repository.IpCoordinatesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeolocationDatabaseManagerTest {

	public static final String IP_ADDRESS = "IP_ADDRESS";

	@Mock
	private IpCoordinatesRepository ipCoordinatesRepository;
	@Mock
	private GeolocationRepository geolocationRepository;
	@InjectMocks
	private GeolocationDatabaseManager manager;

	private Geolocation geolocation;
	private IpCoordinates ipCoordinates;


	@Before
	public void setUp() {
		geolocation = Geolocation.builder().build();
		ipCoordinates = IpCoordinates.builder()
			.geolocation(geolocation)
			.build();
	}

	@Test
	public void getBy_success() {
		when(ipCoordinatesRepository.findByIpAddress(IP_ADDRESS)).thenReturn(Optional.of(ipCoordinates));

		final Optional<IpCoordinates> optIpCoordinates = manager.getBy(IP_ADDRESS);

		assertThat(optIpCoordinates).isPresent();
		assertThat(optIpCoordinates.get()).isEqualTo(ipCoordinates);
	}

	@Test
	public void persist_success() {
		manager.persist(ipCoordinates);

		verify(geolocationRepository).save(geolocation);
		verify(ipCoordinatesRepository).save(ipCoordinates);
	}

}