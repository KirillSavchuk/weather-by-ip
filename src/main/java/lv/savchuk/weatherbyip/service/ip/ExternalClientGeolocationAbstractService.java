package lv.savchuk.weatherbyip.service.ip;

import feign.FeignException;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.ip.IpCoordinatesMapper;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;

public abstract class ExternalClientGeolocationAbstractService<T> implements ExternalClientGeolocationService {

	public IpCoordinates getCoordinates(String ipAddress) throws ExternalClientException {
		try {
			final T resource = getCoordinatesResource(ipAddress);
			validateResource(resource);
			return getMapper().mapFrom(resource);
		} catch (FeignException ex) {
			throw new ExternalClientException(ex);
		}
	}

	protected abstract T getCoordinatesResource(String ipAddress) throws FeignException;

	protected abstract IpCoordinatesMapper<T> getMapper();

	protected abstract void validateResource(T resource) throws ExternalClientException;

}