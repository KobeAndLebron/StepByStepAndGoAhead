package com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.soundsystem.impl;

import org.springframework.stereotype.Component;

import com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.soundsystem.inter.Tire;

@Component("tire")
public class ChaoYangTire implements Tire{

	public void run() {
		System.out.println("Tire is running!");
	}

}
