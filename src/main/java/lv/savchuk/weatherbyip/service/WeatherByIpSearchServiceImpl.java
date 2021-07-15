package lv.savchuk.weatherbyip.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.RequestHistory;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast;
import lv.savchuk.weatherbyip.repository.RequestHistoryRepository;
import lv.savchuk.weatherbyip.service.ip.IpGeolocationManager;
import lv.savchuk.weatherbyip.service.weather.WeatherForecastManager;
import lv.savchuk.weatherbyip.util.AppMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Requester;
import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Weather;
import static lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast.Weather.Temperature;
import static lv.savchuk.weatherbyip.util.AppMessageUtil.ErrorType.SERVICE_UNAVAILABLE;


@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherByIpSearchServiceImpl implements WeatherByIpSearchService {

	private final String SERVICE_UNAVAILABLE_ERROR_MSG = AppMessageUtil.getError(SERVICE_UNAVAILABLE);

	private final IpGeolocationManager ipCoordinatesManager;
	private final WeatherForecastManager weatherForecastManager;
	private final RequestHistoryRepository requestHistoryRepository;

	public RequesterWeatherForecast getWeatherForecastByIp(String ipAddress) {
		try {
			log.info("[IP={}]", ipAddress);

			final IpCoordinates ipCoordinates = ipCoordinatesManager.getCoordinatesByIp(ipAddress);
			log.info("[IP={}] Retrieved coordinates: {}", ipAddress, ipCoordinates);

			final WeatherForecast weatherForecast = weatherForecastManager.getWeatherForecast(ipCoordinates);
			log.info("[IP={}] Retrieved weather forecast: {}", ipAddress, weatherForecast);

			saveRequestHistory(ipCoordinates, weatherForecast);
			return mapToResponse(ipCoordinates, weatherForecast);
		} catch (NotFoundException ex) {
			log.error("Failed...", ex);
			throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, SERVICE_UNAVAILABLE_ERROR_MSG);
		}
	}

	private void saveRequestHistory(IpCoordinates ipCoordinates, WeatherForecast weatherForecast) {
		final RequestHistory requestHistory = RequestHistory.builder()
			.ipCoordinates(ipCoordinates)
			.weatherForecast(weatherForecast)
			.build();
		requestHistoryRepository.save(requestHistory);
	}

	private RequesterWeatherForecast mapToResponse(IpCoordinates ipCoordinates, WeatherForecast weatherForecast) {
		final Temperature temperature = Temperature.builder()
			.actual(weatherForecast.getTempActual())
			.min(weatherForecast.getTempMin())
			.max(weatherForecast.getTempMax())
			.build();
		final Weather weather = Weather.builder()
			.title(weatherForecast.getTitle())
			.description(weatherForecast.getDescription())
			.windSpeed(weatherForecast.getWindSpeed())
			.temperature(temperature)
			.build();
		final Requester requester = Requester.builder()
			.ipAddress(ipCoordinates.getIpAddress())
			.latitude(ipCoordinates.getLatitude())
			.longitude(ipCoordinates.getLongitude())
			.country(ipCoordinates.getGeolocation().getCountry())
			.city(ipCoordinates.getGeolocation().getCity())
			.build();
		return RequesterWeatherForecast.builder()
			.requester(requester)
			.weather(weather)
			.build();
	}

}