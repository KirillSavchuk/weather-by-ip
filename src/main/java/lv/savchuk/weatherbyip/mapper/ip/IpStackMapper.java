package lv.savchuk.weatherbyip.mapper.ip;

import lv.savchuk.weatherbyip.client.ip.IpStackResource;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import lv.savchuk.weatherbyip.model.Coordinates;
import org.springframework.stereotype.Service;

@Service
public class IpStackMapper implements IpGeolocationMapper<IpStackResource> {

	@Override
	public IpGeolocation mapFrom(IpStackResource data) {
		return IpGeolocation.builder()
			.country(data.getCountry())
			.city(data.getCity())
			.coordinates(Coordinates.builder()
				.longitude(data.getLongitude())
				.latitude(data.getLatitude())
				.build())
			.build();
	}

}