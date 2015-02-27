package com.javaworld.sample.helloworld;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.javaworld.sample.service.HelloService;

public class Activator implements BundleActivator {

	ServiceReference<HelloService> helloServiceReference;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@SuppressWarnings("unchecked")
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!!");

		helloServiceReference = (ServiceReference<HelloService>) context
				.getServiceReference(HelloService.class.getName());

		HelloService helloService = (HelloService) context
				.getService(helloServiceReference);
		
		System.out.println(helloService.sayHello());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
		context.ungetService(helloServiceReference);
	}

}
