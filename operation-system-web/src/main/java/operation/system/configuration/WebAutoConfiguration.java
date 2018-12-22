package operation.system.configuration;

import operation.system.inspect.ApplicationStartupListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(ServiceModelAutoConfiguration.class)
public class WebAutoConfiguration {
	public WebAutoConfiguration() {

	}
	
	public static void main(String[] args) {
		System.out.println("init framework config...");
	}

	@Bean
	public ApplicationStartupListener applicationStartListener(){
		System.out.println("我啥时候执行。。。");
		return new ApplicationStartupListener();
	}
}
