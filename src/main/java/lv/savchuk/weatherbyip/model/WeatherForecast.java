package lv.savchuk.weatherbyip.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherForecast {

	private final String weather;
	private final String description;
	private final Float windSpeed;
	private final Temperature temperature;

	@Data
	@Builder
	public static class Temperature {
		private final Float actual;
		private final Float min;
		private final Float max;
	}

}
