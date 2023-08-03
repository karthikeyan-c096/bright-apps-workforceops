package com.brightapps.workforceops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Carlos Rucker
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class WorkforceOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkforceOpsApplication.class, args);
	}

}
