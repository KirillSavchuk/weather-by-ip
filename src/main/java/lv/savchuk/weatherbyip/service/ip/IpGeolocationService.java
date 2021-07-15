package lv.savchuk.weatherbyip.service.ip;

import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.exception.ExternalClientException;

public interface IpGeolocationService {

	IpCoordinates getCoordinatesByIp(String ipAddress) throws ExternalClientException;

}