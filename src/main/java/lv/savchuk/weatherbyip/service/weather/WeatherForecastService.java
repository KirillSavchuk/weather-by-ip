package lv.savchuk.weatherbyip.service.weather;

import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.exception.ExternalClientException;

public interface WeatherForecastService {

	WeatherForecast getWeatherForecast(IpCoordinates ipCoordinates) throws ExternalClientException;

}