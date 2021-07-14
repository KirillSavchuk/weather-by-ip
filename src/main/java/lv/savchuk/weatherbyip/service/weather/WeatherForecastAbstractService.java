package lv.savchuk.weatherbyip.service.weather;

import feign.FeignException;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.mapper.weather.WeatherForecastMapper;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import lv.savchuk.weatherbyip.model.WeatherForecast;

public abstract class WeatherForecastAbstractService<T> implements WeatherForecastService {

	public WeatherForecast getWeatherForecast(IpGeolocation ipGeolocation) throws ExternalClientException {
		try {
			final T resource = getWeatherForecastResource(ipGeolocation);
			validateResource(resource);
			return getMapper().mapFrom(resource);
		} catch (FeignException ex) {
			throw new ExternalClientException(ex);
		}
	}

	protected abstract T getWeatherForecastResource(IpGeolocation ipGeolocation) throws FeignException;

	protected abstract WeatherForecastMapper<T> getMapper();

	protected abstract void validateResource(T resource) throws ExternalClientException;

}