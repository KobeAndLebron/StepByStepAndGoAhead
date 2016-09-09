package com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.soundsystem.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.soundsystem.inter.Car;
import com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.soundsystem.inter.Tire;

@Component(value="benChi")
public class BenChi implements Car{
	// Must be annotated with annotation named AutoWired and the bean will not be injected if do not do this.
	@Autowired
	private Tire tire;
	
	/**
	 *  Even if constructor is not annotated with AutoWired,tire is also injected by Spring DI facilities.

	 * @param tire
	 */
	//@Autowired
	public BenChi(Tire tire){
		System.out.println("Constructor is first invoked and injected bean's hashCode is " + tire.hashCode());
		// Comment to demonstrate injection's order.
		//this.tire = tire;
	}

	@Autowired
	public void init(Tire tire){
		System.out.println("Field is secondly injected and injected bean's hashCode is " + this.tire.hashCode());
		System.out.println("Config method is thirdly invoked and injected bean's hashCode is " + tire.hashCode());
	}
	
	public void run() {
		tire.run();
	}

}
