package lv.savchuk.weatherbyip.service.ip;

import feign.FeignException;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.ip.IpCoordinatesMapper;

public abstract class IpGeolocationAbstractService<T> implements IpGeolocationService {

	public IpCoordinates getCoordinatesByIp(String ipAddress) throws ExternalClientException {
		try {
			final T resource = getIpCoordinatesResource(ipAddress);
			validateResource(resource);
			final IpCoordinates ipCoordinates = getMapper().mapFrom(resource);
			ipCoordinates.setIpAddress(ipAddress);
			return ipCoordinates;
		} catch (FeignException ex) {
			throw new ExternalClientException(ex);
		}
	}

	protected abstract T getIpCoordinatesResource(String ipAddress) throws FeignException;

	protected abstract IpCoordinatesMapper<T> getMapper();

	protected abstract void validateResource(T resource) throws ExternalClientException;

}