package lv.savchuk.weatherbyip.service.weather;

import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.savchuk.weatherbyip.client.weather.OpenWeatherMapClient;
import lv.savchuk.weatherbyip.mapper.weather.OpenWeatherMapMapper;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dto.OpenWeatherMapResource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
@RequiredArgsConstructor
public class ExternalClientOpenWeatherMapService extends ExternalClientWeatherForecastAbstractService<OpenWeatherMapResource> {

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