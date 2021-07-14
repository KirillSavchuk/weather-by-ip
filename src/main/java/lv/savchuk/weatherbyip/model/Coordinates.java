package lv.savchuk.weatherbyip.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coordinates {

	private final Float longitude;
	private final Float latitude;

}