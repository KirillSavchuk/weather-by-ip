package lv.savchuk.weatherbyip.service;

import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.ExternalClientServiceHolder;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
public abstract class ExternalClientManager<T extends ExternalClientService> {

	private final List<ExternalClientServiceHolder<T>> clientServices;

	protected ExternalClientManager(Set<T> clientServices) {
		this.clientServices = clientServices.stream().map(ExternalClientServiceHolder::new).collect(toList());
	}

	protected List<ExternalClientServiceHolder<T>> getValidServices() {
		return clientServices.stream().filter(ExternalClientServiceHolder::isValid).collect(toList());
	}

	protected void handleExternalClientException(ExternalClientException ex, ExternalClientServiceHolder<T> clientService) {
		clientService.updateNextExecutionRetry();
		log.error("External client service '{}' has failed.", clientService.getService().getClass().getSimpleName(), ex);
	}

}