package lv.savchuk.weatherbyip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lv.savchuk.weatherbyip.service.ExternalClientService;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ExternalClientServiceHolder<T extends ExternalClientService> {

	@Getter
	private final T service;
	private LocalDateTime notExecutionRetry;

	public ExternalClientServiceHolder(T service) {
		this.service = service;
		this.notExecutionRetry = LocalDateTime.now();
	}

	public boolean isValid() {
		return LocalDateTime.now().isAfter(notExecutionRetry);
	}

	public void updateNextExecutionRetry() {
		this.notExecutionRetry = notExecutionRetry.with(service.getFailureRecoveryTime());
	}


}