package lv.savchuk.weatherbyip.service.ip;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.config.AppCacheConfig;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeolocationManager {

	private final GeolocationDatabaseManager databaseManager;
	private final GeolocationExternalClientManager clientManager;

	@Cacheable(value = AppCacheConfig.CACHE_GET_COORDINATES_BY_IP_ADDRESS)
	public IpCoordinates getCoordinatesByIp(String ipAddress) throws NotFoundException {
		Optional<IpCoordinates> optIpCoordinates = databaseManager.getBy(ipAddress);
		if (optIpCoordinates.isPresent()) {
			return optIpCoordinates.get();
		}

		final IpCoordinates ipCoordinates = clientManager.getCoordinatesByIp(ipAddress);
		persist(ipCoordinates, ipAddress);
		return ipCoordinates;
	}

	private void persist(IpCoordinates ipCoordinates, String ipAddress) {
		ipCoordinates.setIpAddress(ipAddress);
		databaseManager.persist(ipCoordinates);
	}

}