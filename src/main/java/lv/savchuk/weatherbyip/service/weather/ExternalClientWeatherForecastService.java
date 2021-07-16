package lv.savchuk.weatherbyip.service.weather;

import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.service.ExternalClientService;

public interface ExternalClientWeatherForecastService extends ExternalClientService {

	WeatherForecast getWeatherForecast(IpCoordinates ipCoordinates) throws ExternalClientException;

}