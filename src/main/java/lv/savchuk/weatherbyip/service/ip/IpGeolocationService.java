package lv.savchuk.weatherbyip.service.ip;

import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.dao.IpCoordinates;
import lv.savchuk.weatherbyip.service.ExternalClientService;

public interface IpGeolocationService extends ExternalClientService  {

	IpCoordinates getCoordinatesByIp(String ipAddress) throws ExternalClientException;

}