package lv.savchuk.weatherbyip.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequesterWeatherForecast {

	private final Requester requester;
	private final Weather weather;

	@Data
	@Builder
	public static class Requester {
		private final String ipAddress;
		private final float longitude;
		private final float latitude;
		private final String country;
		private final String city;
	}

	@Data
	@Builder
	public static class Weather {
		private final String title;
		private final String description;
		private final float windSpeed;
		private final Temperature temperature;

		@Data
		@Builder
		public static class Temperature {
			private final float actual;
			private final float min;
			private final float max;
		}
	}

}