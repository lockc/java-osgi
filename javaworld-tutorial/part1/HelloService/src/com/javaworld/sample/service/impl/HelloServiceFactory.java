package com.javaworld.sample.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

import com.javaworld.sample.service.HelloService;

public class HelloServiceFactory implements ServiceFactory<HelloService> {

	private AtomicInteger usageCounter = new AtomicInteger(1);
	
	@Override
	public HelloService getService(Bundle bundle, ServiceRegistration<HelloService> registration) {
		System.out.println("Create object of HelloService for " + bundle.getSymbolicName());
        usageCounter.getAndIncrement();
        System.out.println("Number of bundles using service " + usageCounter);
        HelloService helloService = new HelloServiceImpl();
        return helloService;
	}

	@Override
	public void ungetService(Bundle bundle,
			ServiceRegistration<HelloService> registration, HelloService service) {
		System.out.println("Release object of HelloService for " + bundle.getSymbolicName());
		usageCounter.getAndDecrement();
        System.out.println("Number of bundles using service " + usageCounter);
	}

}
