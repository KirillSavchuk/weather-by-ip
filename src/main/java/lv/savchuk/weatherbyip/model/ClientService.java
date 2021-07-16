package lv.savchuk.weatherbyip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lv.savchuk.weatherbyip.service.ExternalClientService;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ClientService<T extends ExternalClientService> {

	@Getter
	private final T service;
	private LocalDateTime nextExecution;

	public ClientService(T service) {
		this.service = service;
		this.nextExecution = LocalDateTime.now();
	}

	public boolean isValid() {
		return nextExecution.isAfter(LocalDateTime.now());
	}

	public void updateNextExecution() {
		this.nextExecution = nextExecution.with(service.getFailureRecoveryTime());
	}


}