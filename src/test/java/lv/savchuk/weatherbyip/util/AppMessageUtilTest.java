package lv.savchuk.weatherbyip.util;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ResourceBundle;

import static lv.savchuk.weatherbyip.util.AppMessageUtil.ErrorType.SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Ignore //TODO: WTF POWER MOCK?
@RunWith(PowerMockRunner.class)
@PrepareForTest(ResourceBundle.class)
public class AppMessageUtilTest {

	public static final String MESSAGE = "Service is unavailable. Please visit https://www.yahoo.com/news/weather.";

	private ResourceBundle resourceBundle;

	@Before
	public void setUp() {
		ResourceBundle resourceBundle = PowerMockito.mock(ResourceBundle.class);

		PowerMockito.mockStatic(ResourceBundle.class);
		when(ResourceBundle.getBundle("messages")).thenReturn(resourceBundle);

		when(resourceBundle.getString(any())).thenReturn(MESSAGE);
	}

	@Test
	public void getError() {
		final String message = AppMessageUtil.getError(SERVICE_UNAVAILABLE);
		assertThat(message).isEqualTo(MESSAGE);
		verify(resourceBundle).getString("error.service-unavailable");
	}

}