package lv.savchuk.weatherbyip.mapper.ip;

import lv.savchuk.weatherbyip.client.ip.IpStackResource;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import org.springframework.stereotype.Service;

@Service
public class IpStackMapper implements IpCoordinatesMapper<IpStackResource> {

	@Override
	public IpCoordinates mapFrom(IpStackResource data) {
		final Geolocation geolocation = Geolocation.builder()
			.country(data.getCountry())
			.city(data.getCity())
			.build();
		return IpCoordinates.builder()
			.longitude(data.getLongitude())
			.latitude(data.getLatitude())
			.geolocation(geolocation)
			.build();
	}

}