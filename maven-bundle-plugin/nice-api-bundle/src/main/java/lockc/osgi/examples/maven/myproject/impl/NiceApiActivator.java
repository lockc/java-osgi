package lockc.osgi.examples.maven.myproject.impl;

import lockc.osgi.examples.maven.myproject.api.NiceApi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NiceApiActivator implements BundleActivator {
    
    private static final Logger LOG = LoggerFactory.getLogger(NiceApiActivator.class);
    
    private ServiceRegistration<NiceApi> serviceRegistration;
    
    @Override
    @SuppressWarnings("unchecked")
    public void start(BundleContext context) throws Exception {
    
        LOG.info(">>>>>>>> Starting " + this.getClass().getName());
        
        NiceApiServiceFactory factory = new NiceApiServiceFactory();
        serviceRegistration = (ServiceRegistration<NiceApi>) context
                .registerService(NiceApi.class.getName(), factory, null);
        
        LOG.info(">>>>>>>> Service Registered : " + NiceApi.class.getName());
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
    
        LOG.info(">>>>>>>> Stopping " + this.getClass().getName());
        
        serviceRegistration.unregister();
    }
    
}

