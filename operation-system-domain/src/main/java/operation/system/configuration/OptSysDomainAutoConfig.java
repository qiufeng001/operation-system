package operation.system.configuration;

import operation.system.domain.IPaymentReponsitory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackageClasses = { IPaymentReponsitory.class })
/*@ComponentScan(basePackages = {
			"warwolf.administrator.domain.service",
			"warwolf.administrator.domain.manager"			
			})*/
public class OptSysDomainAutoConfig {
	public OptSysDomainAutoConfig() {
		System.out.println("init domain...");
	}
}