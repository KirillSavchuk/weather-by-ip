package lv.savchuk.weatherbyip.service.ip;

import feign.FeignException;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.ip.IpCoordinatesMapper;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;

public abstract class IpGeolocationAbstractService<T> implements IpGeolocationService {

	public IpCoordinates getCoordinatesByIp(String ipAddress) throws ExternalClientException {
		try {
			final T resource = getIpCoordinatesResource(ipAddress);
			validateResource(resource);
			return getMapper().mapFrom(resource);
		} catch (FeignException ex) {
			throw new ExternalClientException(ex);
		}
	}

	protected abstract T getIpCoordinatesResource(String ipAddress) throws FeignException;

	protected abstract IpCoordinatesMapper<T> getMapper();

	protected abstract void validateResource(T resource) throws ExternalClientException;

}