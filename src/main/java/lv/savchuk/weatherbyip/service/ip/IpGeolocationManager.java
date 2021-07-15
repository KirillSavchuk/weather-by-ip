package lv.savchuk.weatherbyip.service.ip;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.repository.GeolocationRepository;
import lv.savchuk.weatherbyip.repository.IpCoordinatesRepository;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class IpGeolocationManager {

	private final IpCoordinatesRepository ipCoordinatesRepository;
	private final GeolocationRepository geolocationRepository;
	private final Set<IpGeolocationService> services;

	public IpCoordinates getCoordinatesByIp(String ipAddress) throws NotFoundException {
		Optional<IpCoordinates> optIpCoordinates = ipCoordinatesRepository.findByIpAddress(ipAddress);
		if (optIpCoordinates.isPresent()) {
			return optIpCoordinates.get();
		}

		final IpCoordinates ipCoordinates = getCoordinatesByIpFromClient(ipAddress)
			.orElseThrow(() -> new NotFoundException(format("IP '%s' geolocation data was not found!", ipAddress)));

		geolocationRepository.save(ipCoordinates.getGeolocation());
		ipCoordinatesRepository.save(ipCoordinates);

		return ipCoordinates;
	}

	public Optional<IpCoordinates> getCoordinatesByIpFromClient(String ipAddress) {
		for (IpGeolocationService service : services) {
			try {
				return Optional.of(service.getCoordinatesByIp(ipAddress));
			} catch (ExternalClientException ex) {
				log.error("Service '{}' failed to get get geolocation by IP: {}.",
					service.getClass().getSimpleName(), ipAddress, ex);
			}
		}
		return Optional.empty();
	}

}