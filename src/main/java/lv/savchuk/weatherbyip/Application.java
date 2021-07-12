package lv.savchuk.weatherbyip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "lv.savchuk.weatherbyip.client")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}