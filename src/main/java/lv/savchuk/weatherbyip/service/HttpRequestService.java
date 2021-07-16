package lv.savchuk.weatherbyip.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class HttpRequestService {

	public static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";

	public String getIpAddress(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(HEADER_X_FORWARDED_FOR))
			.orElse(request.getRemoteAddr());
	}

}
