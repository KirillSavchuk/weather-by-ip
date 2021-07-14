package lv.savchuk.weatherbyip.service.weather;

import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapClient;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapResource;
import lv.savchuk.weatherbyip.mapper.weather.OpenWeatherMapMapper;
import lv.savchuk.weatherbyip.model.Coordinates;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapService extends WeatherForecastAbstractService<OpenWeatherMapResource> {

	@Getter
	private final OpenWeatherMapMapper mapper;
	private final OpenWeatherMapClient client;

	@Override
	protected OpenWeatherMapResource getWeatherForecastResource(IpGeolocation ipGeolocation) throws FeignException {
		final Coordinates coordinates = ipGeolocation.getCoordinates();
		return client.findCurrentWeather(coordinates.getLongitude(), coordinates.getLatitude());
	}

	@Override
	protected void validateResource(OpenWeatherMapResource resource) {
		//TODO: add validation
	}

}