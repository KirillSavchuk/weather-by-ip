package lv.savchuk.weatherbyip.service;

import lv.savchuk.weatherbyip.model.WeatherForecast;

public interface WeatherByIpSearchService {

	WeatherForecast getWeatherForecastByIp(String ipAddress);

}