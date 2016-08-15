package com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 	If specific packages are not defined, scanning will occur from the package of the
 * class that declares this annotation.
 * 
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年8月12日-下午9:21:51
 *
 */
@Configuration
// @ComponentScan(basePackageClasses={BenChi.class})
// @ComponentScan(basePackages={"com.cjs.gohead.spring.ioc.components_scan_and_auto_wired.example.soundsystem"})
@ComponentScan(basePackages={"com"})
/************************************All of above is right.*********************************************/
// Wrong! The behaviour will use the package of the class annotated with ComponentScan.
// @ComponentScan(basePackages={"no-exist PackageName"}) 
public class ComponentScanConfig {

}
