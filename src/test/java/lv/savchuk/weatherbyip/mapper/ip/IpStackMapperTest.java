package lv.savchuk.weatherbyip.mapper.ip;

import lv.savchuk.weatherbyip.client.ip.IpStackResource;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IpStackMapperTest {

	private IpStackMapper mapper;

	private final Float LATITUDE = 1.1f;
	private final Float LONGITUDE = 2.2f;

	@BeforeEach
	void setUp() {
		mapper = new IpStackMapper();
	}

	@Test
	void mapFrom_success() {
		final IpStackResource resource = IpStackResource.builder()
			.country("COUNTRY")
			.city("CITY")
			.latitude(LATITUDE)
			.longitude(LONGITUDE)
			.build();

		final IpGeolocation ipGeolocation = mapper.mapFrom(resource);

		assertThat(ipGeolocation.getCoordinates().getLatitude()).isEqualTo(LATITUDE);
		assertThat(ipGeolocation.getCoordinates().getLongitude()).isEqualTo(LONGITUDE);
	}

}