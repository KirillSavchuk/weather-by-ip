package lv.savchuk.weatherbyip.client.ip;

import lv.savchuk.weatherbyip.client.ResourceMapperTester;
import lv.savchuk.weatherbyip.model.dto.IpStackResource;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class IpStackResourceTest extends ResourceMapperTester {

	public String getFileName() {
		return "ip-stack-response-success.json";
	}

	@Test
	public void testMapping() throws IOException {
		final IpStackResource resource = getResource(IpStackResource.class);
		assertThat(resource.getCountry()).isEqualTo("Latvia");
		assertThat(resource.getCity()).isEqualTo("Riga");
		assertThat(resource.getLatitude()).isEqualTo(56.96017074584961f);
		assertThat(resource.getLongitude()).isEqualTo(24.134309768676758f);
	}

}