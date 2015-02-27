package com.javaworld.sample.service.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.javaworld.sample.service.HelloService;

public class HelloServiceActivator implements BundleActivator {
	ServiceRegistration helloServiceRegistration;

	public void start(BundleContext context) throws Exception {
		HelloService helloService = new HelloServiceImpl();
		helloServiceRegistration = context.registerService(
				HelloService.class.getName(), helloService, null);
	}

	public void stop(BundleContext context) throws Exception {
		helloServiceRegistration.unregister();
	}

}
