package lv.savchuk.weatherbyip.service;

import javassist.NotFoundException;
import lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast;

public interface WeatherByIpSearchService {

	RequesterWeatherForecast getWeatherForecastByIp(String ipAddress) throws NotFoundException;

}