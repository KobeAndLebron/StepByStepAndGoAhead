package com.cjs.gohead.spring.ioc.components_scan_and_auto_wired;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.config.ComponentScanConfig;
import com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.soundsystem.inter.Car;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ComponentScanConfig.class})
public class TestAutoWired {

	@Autowired
	private Car car;
	
	@Test
	public void testComponentIsWired(){
		assertNotEquals(null, car);
		car.run();
	}
}
