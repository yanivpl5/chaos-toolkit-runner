package com.tikalk.chaostoolkitrunner;

import com.tikalk.chaostoolkitrunner.service.ExperimentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChaosToolkitRunnerApplicationTests {
	@Autowired
	private ExperimentService experimentService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testLaunchExperiment() {
		experimentService.launch();
	}
}
