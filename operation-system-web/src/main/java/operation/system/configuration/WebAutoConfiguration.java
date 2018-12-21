package operation.system.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;


@Configuration
@AutoConfigureAfter(ServiceModelAutoConfiguration.class)
public class WebAutoConfiguration {
	public WebAutoConfiguration() {

	}
	
	public static void main(String[] args) {
		System.out.println("init framework config...");
	}
}
