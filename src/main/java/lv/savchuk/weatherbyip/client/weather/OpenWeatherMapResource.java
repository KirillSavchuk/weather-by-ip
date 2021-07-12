package lv.savchuk.weatherbyip.client.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapResource {

	Integer code;
	String name;
	@JsonProperty("coord")
	Coordinates coordinates;
	List<Weather> weather;
	@JsonProperty("main")
	Temperature temperature;
	Wind wind;
	Integer visibility;
	String base;

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Coordinates {
		@JsonProperty("lon")
		Float longitude;
		@JsonProperty("lat")
		Float latitude;
	}

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Weather {
		String id;
		String main;
		String description;
		String icon;
	}

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Temperature {
		@JsonProperty("temp")
		Float actual;
		@JsonProperty("feels_like")
		Float feelsLike;
		@JsonProperty("temp_min")
		Float min;
		@JsonProperty("temp_max")
		Float max;
		Float pressure;
		Float humidity;
	}

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Wind {
		Float speed;
		@JsonProperty("deg")
		Integer degrees;
		@JsonProperty("temp_min")
		Float min;
		@JsonProperty("temp_max")
		Float max;
		Float pressure;
		Float humidity;
	}

}