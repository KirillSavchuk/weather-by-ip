package lv.savchuk.weatherbyip.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherForecast {

	private final String weather;

}
