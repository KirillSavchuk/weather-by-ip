package lv.savchuk.weatherbyip.service.ip;

import javassist.NotFoundException;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
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
public class GeolocationManagerTest {

	public static final String IP_ADDRESS = "IP_ADDRESS";

	@Mock
	private GeolocationDatabaseManager databaseManager;
	@Mock
	private GeolocationExternalClientManager clientManager;
	@InjectMocks
	private GeolocationManager manager;

	private IpCoordinates ipCoordinates;

	@Before
	public void setUp() {
		ipCoordinates = IpCoordinates.builder().build();
	}

	@Test
	public void getCoordinates_success_getFromDB() throws NotFoundException {
		when(databaseManager.getBy(IP_ADDRESS)).thenReturn(Optional.of(ipCoordinates));

		final IpCoordinates actualCoordinates = manager.getCoordinates(IP_ADDRESS);

		assertThat(actualCoordinates).isEqualTo(ipCoordinates);
		verify(databaseManager, never()).persist(ipCoordinates);
	}

	@Test
	public void getCoordinates_success_getFromClient() throws NotFoundException {
		when(databaseManager.getBy(IP_ADDRESS)).thenReturn(Optional.empty());
		when(clientManager.getCoordinates(IP_ADDRESS)).thenReturn(ipCoordinates);

		final IpCoordinates actualCoordinates = manager.getCoordinates(IP_ADDRESS);

		assertThat(actualCoordinates).isEqualTo(ipCoordinates);
		assertThat(ipCoordinates.getIpAddress()).isEqualTo(IP_ADDRESS);
		verify(databaseManager).persist(ipCoordinates);
	}


}