package lv.savchuk.weatherbyip.service;

import lombok.extern.slf4j.Slf4j;
import lv.savchuk.weatherbyip.exception.ExternalClientException;
import lv.savchuk.weatherbyip.model.ClientService;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
public abstract class ClientManager<T extends ExternalClientService> {

	private final List<ClientService<T>> clientServices;

	protected ClientManager(Set<T> clientServices) {
		this.clientServices = clientServices.stream().map(ClientService::new).collect(toList());
	}

	protected List<ClientService<T>> getValidServices() {
		return clientServices.stream().filter(ClientService::isValid).collect(toList());
	}

	protected void handleExternalClientException(ExternalClientException ex, ClientService<T> clientService) {
		clientService.updateNextExecution();
		log.error("External client service '{}' has failed.", clientService.getService().getClass().getSimpleName(), ex);
	}

}