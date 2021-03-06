package lv.savchuk.weatherbyip.client.ip;

import lv.savchuk.weatherbyip.client.ResourceMapperTester;
import lv.savchuk.weatherbyip.model.dto.IpApiResource;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class IpApiResourceTest extends ResourceMapperTester {

	public String getFileName() {
		return "ip-api-response-success.json";
	}

	@Test
	public void testMapping() throws IOException {
		final IpApiResource resource = getResource(IpApiResource.class);
		assertThat(resource.getStatus()).isEqualTo("success");
		assertThat(resource.getMessage()).isNull();
		assertThat(resource.getCountry()).isEqualTo("Latvia");
		assertThat(resource.getCity()).isEqualTo("Riga");
		assertThat(resource.getLatitude()).isEqualTo(56.9496f);
		assertThat(resource.getLongitude()).isEqualTo(24.0978f);
	}

}