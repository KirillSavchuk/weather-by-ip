package lv.savchuk.weatherbyip.service.weather;

import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapClient;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapResource;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.mapper.weather.OpenWeatherMapMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapService extends WeatherForecastAbstractService<OpenWeatherMapResource> {

	@Getter
	private final OpenWeatherMapMapper mapper;
	private final OpenWeatherMapClient client;

	@Override
	protected OpenWeatherMapResource getWeatherForecastResource(IpCoordinates ipCoordinates) throws FeignException {
		return client.findCurrentWeather(ipCoordinates.getLongitude(), ipCoordinates.getLatitude());
	}

	@Override
	protected void validateResource(OpenWeatherMapResource resource) {
		//TODO: add validation
	}

}