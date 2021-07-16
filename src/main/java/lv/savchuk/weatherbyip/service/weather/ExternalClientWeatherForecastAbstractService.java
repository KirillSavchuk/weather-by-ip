package lv.savchuk.weatherbyip.service.weather;

import feign.FeignException;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.weather.WeatherForecastMapper;

public abstract class ExternalClientWeatherForecastAbstractService<T> implements ExternalClientWeatherForecastService {

	public WeatherForecast getWeatherForecast(IpCoordinates ipCoordinates) throws ExternalClientException {
		try {
			final T resource = getWeatherForecastResource(ipCoordinates);
			validateResource(resource);
			return getMapper().mapFrom(resource);
		} catch (FeignException ex) {
			throw new ExternalClientException(ex);
		}
	}

	protected abstract T getWeatherForecastResource(IpCoordinates ipCoordinates) throws FeignException;

	protected abstract WeatherForecastMapper<T> getMapper();

	protected abstract void validateResource(T resource) throws ExternalClientException;

}