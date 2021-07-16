package lv.savchuk.weatherbyip.mapper.ip;

import lv.savchuk.weatherbyip.model.dto.IpApiResource;
import lv.savchuk.weatherbyip.model.dao.Geolocation;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import org.springframework.stereotype.Service;

@Service
public class IpApiMapper implements IpCoordinatesMapper<IpApiResource> {

	@Override
	public IpCoordinates mapFrom(IpApiResource data) {
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