package lv.savchuk.weatherbyip.client.ip;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * https://ipstack.com/documentation
 */
@FeignClient(
	name = "ip-stack",
	url = "http://api.ipstack.com",
	configuration = IpStackClient.CustomConfig.class
)
public interface IpStackClient {

	@GetMapping(path = "/")
	IpStackResource findGeolocationByIp(
		@PathVariable("ipAddress") String ipAddress,
		@RequestParam("fields") String[] fields
	);

	class CustomConfig {

		@Value("${client.ip-stack.api-key}")
		private String apiKey;

		@Bean
		RequestInterceptor requestInterceptor() {
			return requestTemplate -> requestTemplate.query("access_key", apiKey);
		}

	}

}