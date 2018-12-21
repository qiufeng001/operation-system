package operation.system.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"operation.system.manager"})
public class ManagerAutoConifg {
	public static void main(String[] args) {
		System.out.println("init manager config...");
	}
}