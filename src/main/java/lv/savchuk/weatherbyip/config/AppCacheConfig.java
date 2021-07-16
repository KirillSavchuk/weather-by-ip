package lv.savchuk.weatherbyip.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class AppCacheConfig {

	public static final String CACHE_GET_COORDINATES_BY_IP_ADDRESS = "get-coordinates-by-ip-address";
	public static final String CACHE_GET_WEATHER_FORECAST_BY_IP_COORDINATES = "get-weather-forecast-by-ip-coordinates";

	@Bean
	Config config() {
		final Config config = new Config();
		config.getMapConfigs().put(CACHE_GET_COORDINATES_BY_IP_ADDRESS, getCoordinatesByIpAddressCache());
		config.getMapConfigs().put(CACHE_GET_WEATHER_FORECAST_BY_IP_COORDINATES, getWeatherForecastByIpCoordinates());
		return config;
	}

	private MapConfig getCoordinatesByIpAddressCache() {
		return getMapConfig(24 * 60);
	}

	private MapConfig getWeatherForecastByIpCoordinates() {
		return getMapConfig(60);
	}

	private MapConfig getMapConfig(int timeToLiveMinutes) {
		final MapConfig config = new MapConfig();
		config.setTimeToLiveSeconds(timeToLiveMinutes * 60);
		return config;
	}

}