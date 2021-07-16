package lv.savchuk.weatherbyip.service.ip;

import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.repository.GeolocationRepository;
import lv.savchuk.weatherbyip.repository.IpCoordinatesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeolocationDatabaseManager {

	private final IpCoordinatesRepository ipCoordinatesRepository;
	private final GeolocationRepository geolocationRepository;

	public Optional<IpCoordinates> getBy(String ipAddress) {
		return ipCoordinatesRepository.findByIpAddress(ipAddress);
	}

	public void persist(IpCoordinates ipCoordinates) {
		geolocationRepository.save(ipCoordinates.getGeolocation());
		ipCoordinatesRepository.save(ipCoordinates);
	}

}