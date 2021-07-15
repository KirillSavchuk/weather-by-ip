package lv.savchuk.weatherbyip.exception;

import feign.FeignException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.ByteBuffer;
import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalClientExceptionTest {

	private static final String ERROR_MESSAGE = "ERROR_MESSAGE";
	private static final String RESPONSE = "RESPONSE";

	@Mock
	private FeignException feignException;

	@Test
	public void test_toString_constructorWithMessageAndResponse() {
		final ExternalClientException ex = new ExternalClientException(ERROR_MESSAGE, RESPONSE);

		assertThat(ex.toString())
			.isEqualTo(format("External client error: %s. Client response: %s. Initial exception: null.", ERROR_MESSAGE, RESPONSE));
	}

	@Test
	public void test_toString_constructorWithFeignException() {
		final String FEIGN_ERROR = "FEIGN_ERROR";
		when(feignException.getMessage()).thenReturn(FEIGN_ERROR);
		when(feignException.responseBody()).thenReturn(Optional.of(ByteBuffer.wrap(RESPONSE.getBytes())));
		when(feignException.toString()).thenReturn(FEIGN_ERROR);

		final ExternalClientException ex = new ExternalClientException(feignException);

		assertThat(ex.toString())
			.isEqualTo(format("External client error: %s. Client response: %s. Initial exception: %s.", FEIGN_ERROR, RESPONSE, FEIGN_ERROR));
	}

}