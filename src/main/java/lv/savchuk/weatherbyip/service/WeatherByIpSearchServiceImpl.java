package lv.savchuk.weatherbyip.service;

import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.WeatherForecast;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeatherByIpSearchServiceImpl implements WeatherByIpSearchService {

	public WeatherForecast getWeatherForecastByIp(String ipAddress) {
		return WeatherForecast.builder()
			.weather(ipAddress)
			.build();
	}

}