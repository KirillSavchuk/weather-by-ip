package lv.savchuk.weatherbyip.mapper.weather;

import lv.savchuk.weatherbyip.mapper.ModelMapper;
import lv.savchuk.weatherbyip.model.WeatherForecast;

public interface WeatherForecastMapper<FROM> extends ModelMapper<FROM, WeatherForecast> {

	WeatherForecast mapFrom(FROM data);

}