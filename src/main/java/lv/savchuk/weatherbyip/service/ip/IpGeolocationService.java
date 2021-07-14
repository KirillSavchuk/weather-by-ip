package lv.savchuk.weatherbyip.service.ip;

import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.IpGeolocation;

public interface IpGeolocationService {

	IpGeolocation getGeolocationByIp(String ipAddress) throws ExternalClientException;

}