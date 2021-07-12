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
public class IpApiResource {

	String status;
	String message;
	String country;
	String city;
	@JsonProperty("lat")
	Float latitude;
	@JsonProperty("lon")
	Float longitude;

}