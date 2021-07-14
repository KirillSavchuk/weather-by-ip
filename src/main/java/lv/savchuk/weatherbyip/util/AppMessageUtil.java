package lv.savchuk.weatherbyip.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ResourceBundle;

import static java.lang.String.format;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppMessageUtil {

	private final static ResourceBundle APP_MESSAGES = ResourceBundle.getBundle("messages");

	public static String getError(ErrorType error) {
		return APP_MESSAGES.getString(format("error.%s", error.getType()));
	}

	@Getter
	@RequiredArgsConstructor
	public enum ErrorType {
		SERVICE_UNAVAILABLE("service-unavailable");

		private final String type;
	}

}