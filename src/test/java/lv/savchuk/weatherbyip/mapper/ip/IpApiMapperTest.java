package lv.savchuk.weatherbyip.mapper.ip;

import lv.savchuk.weatherbyip.client.ip.IpApiResource;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IpApiMapperTest {

	private IpApiMapper mapper;

	private final static Float LATITUDE = 1.1f;
	private final static Float LONGITUDE = 2.2f;
	private final static String COUNTRY = "COUNTRY";
	private final static String CITY = "CITY";

	@Before
	public void setUp() {
		mapper = new IpApiMapper();
	}

	@Test
	public void mapFrom_success() {
		final IpApiResource resource = IpApiResource.builder()
			.country(COUNTRY)
			.city(CITY)
			.latitude(LATITUDE)
			.longitude(LONGITUDE)
			.build();

		final IpCoordinates ipCoordinates = mapper.mapFrom(resource);

		final Geolocation geolocation = ipCoordinates.getGeolocation();
		assertThat(ipCoordinates.getLatitude()).isEqualTo(LATITUDE);
		assertThat(ipCoordinates.getLongitude()).isEqualTo(LONGITUDE);
		assertThat(geolocation.getCountry()).isEqualTo(COUNTRY);
		assertThat(geolocation.getCity()).isEqualTo(CITY);
	}

}