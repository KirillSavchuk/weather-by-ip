package lv.savchuk.weatherbyip.client.ip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpStackResource {

	@JsonProperty("country_name")
	String country;
	String city;
	Float latitude;
	Float longitude;
	Boolean success;
	Error error;

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Error {
		Integer code;
		String type;
		String info;
	}

}