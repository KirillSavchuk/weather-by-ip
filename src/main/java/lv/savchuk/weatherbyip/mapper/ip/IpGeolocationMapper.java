package lv.savchuk.weatherbyip.mapper.ip;

import lv.savchuk.weatherbyip.mapper.ModelMapper;
import lv.savchuk.weatherbyip.model.IpGeolocation;

public interface IpGeolocationMapper<FROM> extends ModelMapper<FROM, IpGeolocation> {

	IpGeolocation mapFrom(FROM data);

}