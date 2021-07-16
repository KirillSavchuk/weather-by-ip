package lv.savchuk.weatherbyip.service.ip;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.repository.GeolocationRepository;
import lv.savchuk.weatherbyip.repository.IpCoordinatesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IpGeolocationManager {

	private final IpCoordinatesRepository ipCoordinatesRepository;
	private final GeolocationRepository geolocationRepository;
	private final IpGeolocationClientManager clientManager;

	public IpCoordinates getCoordinatesByIp(String ipAddress) throws NotFoundException {
		Optional<IpCoordinates> optIpCoordinates = ipCoordinatesRepository.findByIpAddress(ipAddress);
		if (optIpCoordinates.isPresent()) {
			return optIpCoordinates.get();
		}

		final IpCoordinates ipCoordinates = clientManager.getCoordinatesByIp(ipAddress);
		persist(ipCoordinates, ipAddress);
		return ipCoordinates;
	}

	private void persist(IpCoordinates ipCoordinates, String ipAddress) {
		ipCoordinates.setIpAddress(ipAddress);
		geolocationRepository.save(ipCoordinates.getGeolocation());
		ipCoordinatesRepository.save(ipCoordinates);
	}

}