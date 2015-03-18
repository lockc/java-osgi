package com.javaworld.sample.service.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.javaworld.sample.service.HelloService;

public class HelloServiceActivator implements BundleActivator {
	private ServiceRegistration<HelloService> helloServiceRegistration;

	@SuppressWarnings("unchecked")
	public void start(BundleContext context) throws Exception {
		// HelloService helloService = new HelloServiceImpl();
		// helloServiceRegistration = context.registerService(
		// HelloService.class.getName(), helloService, null);

		HelloServiceFactory helloServiceFactory = new HelloServiceFactory();
		helloServiceRegistration = (ServiceRegistration<HelloService>) context
				.registerService(HelloService.class.getName(),
						helloServiceFactory, null);
	}

	public void stop(BundleContext context) throws Exception {
		helloServiceRegistration.unregister();
	}

}
