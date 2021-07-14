package lv.savchuk.weatherbyip.service.ip;

import feign.FeignException;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.ip.IpGeolocationMapper;
import lv.savchuk.weatherbyip.model.IpGeolocation;

public abstract class IpGeolocationAbstractService<T> implements IpGeolocationService {

	public IpGeolocation getGeolocationByIp(String ipAddress) throws ExternalClientException {
		try {
			final T resource = getIpGeolocationResource(ipAddress);
			validateResource(resource);
			return getMapper().mapFrom(resource);
		} catch (FeignException ex) {
			throw new ExternalClientException(ex);
		}
	}

	protected abstract T getIpGeolocationResource(String ipAddress) throws FeignException;

	protected abstract IpGeolocationMapper<T> getMapper();

	protected abstract void validateResource(T resource) throws ExternalClientException;

}