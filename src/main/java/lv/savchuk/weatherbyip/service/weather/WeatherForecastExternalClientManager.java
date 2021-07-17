package lv.savchuk.weatherbyip.service.weather;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.ExternalClientServiceHolder;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.service.ExternalClientManager;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Service
public class WeatherForecastExternalClientManager extends ExternalClientManager<ExternalClientWeatherForecastService> {

	public WeatherForecastExternalClientManager(Set<ExternalClientWeatherForecastService> clientServices) {
		super(clientServices);
	}

	public WeatherForecast getWeatherForecast(IpCoordinates ipCoordinates) throws NotFoundException {
		for (ExternalClientServiceHolder<ExternalClientWeatherForecastService> clientService : getValidServices()) {
			try {
				return clientService.getService().getWeatherForecast(ipCoordinates);
			} catch (ExternalClientException ex) {
				handleExternalClientException(ex, clientService);
			}
		}
		throw new NotFoundException(format("Weather Forecast was not found for IP: %s.", ipCoordinates.getIpAddress()));
	}

}