package operation.system.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"operation.system.service"})
public class ServiceAutoConifg {
	public static void main(String[] args) {
		System.out.println("init service config ...");
	}
}