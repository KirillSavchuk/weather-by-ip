package lv.savchuk.weatherbyip.mapper.ip;

import lv.savchuk.weatherbyip.client.ip.IpApiResource;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import lv.savchuk.weatherbyip.model.Coordinates;
import org.springframework.stereotype.Service;

@Service
public class IpApiMapper implements IpGeolocationMapper<IpApiResource> {

	@Override
	public IpGeolocation mapFrom(IpApiResource data) {
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