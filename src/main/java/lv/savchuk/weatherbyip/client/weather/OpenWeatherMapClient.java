package lv.savchuk.weatherbyip.client.weather;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * https://openweathermap.org/current
 */
@FeignClient(
	name = "open-weather-map",
	url = "http://api.openweathermap.org/data/2.5",
	configuration = OpenWeatherMapClient.CustomConfig.class
)
public interface OpenWeatherMapClient {

	@GetMapping(value = "/weather")
	OpenWeatherMapResource findCurrentWeather(
		@RequestParam("lon") Float longitude,
		@RequestParam("lat") Float latitude
	);

	class CustomConfig {

		@Value("${client.open-weather-map.api-key}")
		private String apiKey;

		@Bean
		RequestInterceptor requestInterceptor() {
			return requestTemplate -> requestTemplate.query("appid", apiKey);
		}

	}

}