package lv.savchuk.weatherbyip.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.mapper.RequesterWeatherForecastMapper;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.model.dao.RequestHistory;
import lv.savchuk.weatherbyip.model.dao.WeatherForecast;
import lv.savchuk.weatherbyip.model.dto.RequesterWeatherForecast;
import lv.savchuk.weatherbyip.repository.IpCoordinatesRepository;
import lv.savchuk.weatherbyip.repository.RequestHistoryRepository;
import lv.savchuk.weatherbyip.repository.WeatherForecastRepository;
import lv.savchuk.weatherbyip.service.ip.GeolocationManager;
import lv.savchuk.weatherbyip.service.weather.WeatherForecastManager;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherByIpSearchServiceImpl implements WeatherByIpSearchService {

	private final GeolocationManager ipCoordinatesManager;
	private final WeatherForecastManager weatherForecastManager;
	private final RequestHistoryRepository requestHistoryRepository;
	private final IpCoordinatesRepository coordinatesRepository;
	private final WeatherForecastRepository weatherForecastRepository;
	private final RequesterWeatherForecastMapper responseMapper;

	public RequesterWeatherForecast getWeatherForecastByIp(String ipAddress) throws NotFoundException {
		log.info("[IP={}]", ipAddress);

		final IpCoordinates ipCoordinates = ipCoordinatesManager.getCoordinatesByIp(ipAddress);
		log.info("[IP={}] Retrieved coordinates: {}", ipAddress, ipCoordinates);

		final WeatherForecast weatherForecast = weatherForecastManager.getWeatherForecast(ipCoordinates);
		log.info("[IP={}] Retrieved weather forecast: {}", ipAddress, weatherForecast);

		saveRequestHistory(ipCoordinates, weatherForecast);
		return responseMapper.mapFrom(ipCoordinates, weatherForecast);
	}

	private void saveRequestHistory(IpCoordinates ipCoordinates, WeatherForecast weatherForecast) {
		//TODO: do something with this bad work-around: cache entities
		final RequestHistory requestHistory = RequestHistory.builder()
			.ipCoordinates(coordinatesRepository.findById(ipCoordinates.getId()).get())
			.weatherForecast(weatherForecastRepository.findById(weatherForecast.getId()).get())
			.build();
		requestHistoryRepository.saveAndFlush(requestHistory);
	}

}