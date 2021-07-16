package lv.savchuk.weatherbyip.service.weather;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.ClientService;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.service.ClientManager;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Service
public class WeatherForecastClientManager extends ClientManager<WeatherForecastService> {

	private WeatherForecastClientManager(Set<WeatherForecastService> clientServices) {
		super(clientServices);
	}

	public WeatherForecast getCoordinatesByIp(IpCoordinates ipCoordinates) throws NotFoundException {
		for (ClientService<WeatherForecastService> clientService : getValidServices()) {
			try {
				return clientService.getService().getWeatherForecast(ipCoordinates);
			} catch (ExternalClientException ex) {
				handleExternalClientException(ex, clientService);
			}
		}
		throw new NotFoundException(format("Weather Forecast was not found for IP: %s.", ipCoordinates.getIpAddress()));
	}

}