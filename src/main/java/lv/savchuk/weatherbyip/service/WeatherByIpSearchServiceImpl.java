package lv.savchuk.weatherbyip.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.db.entity.RequestHistory;
import lv.savchuk.weatherbyip.db.repository.RequestHistoryRepository;
import lv.savchuk.weatherbyip.model.IpGeolocation;
import lv.savchuk.weatherbyip.model.WeatherForecast;
import lv.savchuk.weatherbyip.service.ip.IpGeolocationManager;
import lv.savchuk.weatherbyip.service.weather.WeatherForecastManager;
import lv.savchuk.weatherbyip.util.AppMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;
import java.util.concurrent.Callable;

import static lv.savchuk.weatherbyip.util.AppMessageUtil.ErrorType.SERVICE_UNAVAILABLE;
import static lv.savchuk.weatherbyip.util.JsonUtil.toJson;


@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherByIpSearchServiceImpl implements WeatherByIpSearchService {

	private final String SERVICE_UNAVAILABLE_ERROR_MSG = AppMessageUtil.getError(SERVICE_UNAVAILABLE);

	private final IpGeolocationManager ipCoordinatesManager;
	private final WeatherForecastManager weatherForecastManager;
	private final RequestHistoryRepository requestHistoryRepository;

	public WeatherForecast getWeatherForecastByIp(String ipAddress) {
		log.info("[IP={}]", ipAddress);
		final IpGeolocation ipGeolocation = getOrThrow(() -> ipCoordinatesManager.getGeolocationByIp(ipAddress));
		log.info("[IP={}] Retrieved coordinates: {}", ipAddress, ipGeolocation);
		final WeatherForecast weatherForecast = getOrThrow(() -> weatherForecastManager.getWeatherForecast(ipGeolocation));
		log.info("[IP={}] Retrieved weather forecast: {}", ipAddress, weatherForecast);
		saveRequestHistory(ipAddress, ipGeolocation, weatherForecast);
		return weatherForecast;
	}

	@SneakyThrows
	private <T> T getOrThrow(Callable<Optional<T>> optCall) {
		return optCall.call()
			.orElseThrow(() -> new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, SERVICE_UNAVAILABLE_ERROR_MSG));
	}

	private void saveRequestHistory(String ipAddress, IpGeolocation ipGeolocation, WeatherForecast weatherForecast) {
		final RequestHistory requestHistory = RequestHistory.builder()
			.ipAddress(ipAddress)
			.country(ipGeolocation.getCountry())
			.city(ipGeolocation.getCity())
			.weather(toJson(weatherForecast))
			.build();
		requestHistoryRepository.save(requestHistory);
	}

}