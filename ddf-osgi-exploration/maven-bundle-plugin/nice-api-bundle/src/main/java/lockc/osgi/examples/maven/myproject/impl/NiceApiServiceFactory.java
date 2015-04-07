package lockc.osgi.examples.maven.myproject.impl;

import lockc.osgi.examples.maven.myproject.api.NiceApi;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NiceApiServiceFactory implements ServiceFactory<NiceApi> {

    private static final Logger LOG = LoggerFactory.getLogger(NiceApiServiceFactory.class);
    
    @Override
    public NiceApi getService(Bundle bundle, ServiceRegistration<NiceApi> registration) {
            
        LOG.info("Getting service from factory : " + getClass().getName());
        return new NiceApiImpl();
    }

    @Override
    public void ungetService(Bundle bundle, ServiceRegistration<NiceApi> registration, NiceApi service) {
    
        
    }
    
}
