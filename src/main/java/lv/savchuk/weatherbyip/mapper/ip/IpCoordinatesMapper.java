package lv.savchuk.weatherbyip.mapper.ip;

import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.mapper.ModelMapper;

public interface IpCoordinatesMapper<FROM> extends ModelMapper<FROM, IpCoordinates> {

	IpCoordinates mapFrom(FROM data);

}