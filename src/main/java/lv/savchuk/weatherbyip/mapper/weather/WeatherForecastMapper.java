package lv.savchuk.weatherbyip.mapper.weather;

import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.mapper.ModelMapper;

public interface WeatherForecastMapper<FROM> extends ModelMapper<FROM, WeatherForecast> {

	WeatherForecast mapFrom(FROM data);

}