package lv.savchuk.weatherbyip.service.ip;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.ExternalClientServiceHolder;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.service.ExternalClientManager;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Service
public class GeolocationExternalClientManager extends ExternalClientManager<ExternalClientGeolocationService> {

	private GeolocationExternalClientManager(Set<ExternalClientGeolocationService> clientServices) {
		super(clientServices);
	}

	public IpCoordinates getCoordinatesByIp(String ipAddress) throws NotFoundException {
		for (ExternalClientServiceHolder<ExternalClientGeolocationService> clientService : getValidServices()) {
			try {
				return clientService.getService().getCoordinatesByIp(ipAddress);
			} catch (ExternalClientException ex) {
				handleExternalClientException(ex, clientService);
			}
		}
		throw new NotFoundException(format("IP '%s' geolocation data was not found!", ipAddress));
	}

}