package lv.savchuk.weatherbyip.client.ip;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * https://ip-api.com/docs/api:json
 */
@FeignClient(
	name = "ip-api",
	url = "${client.ip-api.url}"
)
public interface IpApiClient {

	@GetMapping(path = "/{ipAddress}")
	IpApiResource findGeolocationByIp(
		@PathVariable("ipAddress") String ipAddress,
		@RequestParam("fields") String[] fields
	);

}