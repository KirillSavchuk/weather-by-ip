package lv.savchuk.weatherbyip.service.ip;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.client.ip.IpApiClient;
import lv.savchuk.weatherbyip.model.dto.IpApiResource;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.ip.IpApiMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalClientIpApiService extends ExternalClientGeolocationAbstractService<IpApiResource> {

	@Getter
	private final IpApiMapper mapper;
	private final IpApiClient client;

	private final static String[] QUERY_FIELDS = new String[]{"status", "message", "country", "city", "lat", "lon"};

	@Override
	protected IpApiResource getIpCoordinatesResource(String ipAddress) {
		return client.findGeolocationByIp(ipAddress, QUERY_FIELDS);
	}

	@Override
	protected void validateResource(IpApiResource resource) throws ExternalClientException {
		//TODO: add validation
	}

}