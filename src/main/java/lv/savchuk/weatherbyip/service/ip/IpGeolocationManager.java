package lv.savchuk.weatherbyip.service.ip;

import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IpGeolocationManager {

	private final Set<IpGeolocationService> services;

	public IpGeolocationManager(Set<IpGeolocationService> services) {
		this.services = services;
	}

	public Optional<IpGeolocation> getGeolocationByIp(String ipAddress) {
		for (IpGeolocationService service : services) {
			try {
				return Optional.of(service.getGeolocationByIp(ipAddress));
			} catch (ExternalClientException ex) {
				log.error("Service '{}' failed to get get geolocation by IP: {}.",
					service.getClass().getSimpleName(), ipAddress, ex);
			}
		}
		return Optional.empty();
	}

}