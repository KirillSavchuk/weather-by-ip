package lv.savchuk.weatherbyip.service.ip;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.client.ip.IpStackClient;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.ip.IpStackMapper;
import lv.savchuk.weatherbyip.model.dto.IpStackResource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
@RequiredArgsConstructor
public class ExternalClientIpStackService extends ExternalClientGeolocationAbstractService<IpStackResource> {

	@Getter
	private final IpStackMapper mapper;
	private final IpStackClient client;

	private final static String[] QUERY_FIELDS = new String[]{"country_name", "city", "latitude", "longitude"};

	@Override
	public IpStackResource getCoordinatesResource(String ipAddress) {
		return client.findGeolocationByIp(ipAddress, QUERY_FIELDS);
	}

	@Override
	protected void validateResource(IpStackResource resource) throws ExternalClientException {
		if (resource.getError() != null) {
			throw new ExternalClientException(resource.getError().getInfo(), resource.toString());
		}
	}

}