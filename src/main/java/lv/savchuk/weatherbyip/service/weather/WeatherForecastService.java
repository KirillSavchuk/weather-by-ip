package lv.savchuk.weatherbyip.service.weather;

import lv.savchuk.weatherbyip.model.IpGeolocation;
import lv.savchuk.weatherbyip.model.WeatherForecast;

public interface WeatherForecastService {

	WeatherForecast getWeatherForecast(IpGeolocation ipGeolocation);

}