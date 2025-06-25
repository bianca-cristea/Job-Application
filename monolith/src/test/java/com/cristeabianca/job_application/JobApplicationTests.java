package com.cristeabianca.job_application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class JobApplicationTests {

	@Test
	void contextLoads() {
		new ApplicationContextRunner()
				.withUserConfiguration(JobApplication.class)
				.run(context -> {
					assert context != null;
				});
	}
}
