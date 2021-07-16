package lv.savchuk.weatherbyip.service;

import java.time.LocalTime;

public interface ExternalClientService {

	/**
	 * If external client return error, service will not be executed again unless the given time is passed.
	 */
	default LocalTime getFailureRecoveryTime() {
		return LocalTime.of(0, 5); //TODO: parametrize per each client
	}

}