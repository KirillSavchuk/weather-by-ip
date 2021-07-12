package lv.savchuk.weatherbyip.client.ip;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * https://ip-api.com/docs/api:json
 */
@FeignClient(name = "ip-api", url = "http://ip-api.com/json")
public interface IpApiClient {

	@GetMapping(value = "/{ipAddress}")
	IpApiResource findGeolocationByIp(
		@Param("ipAddress") String ipAddress,
		@RequestParam("fields") String[] fields
	);

}