package lv.savchuk.weatherbyip.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IpGeolocation {

	private final String country;
	private final String city;
	private final Coordinates coordinates;

}