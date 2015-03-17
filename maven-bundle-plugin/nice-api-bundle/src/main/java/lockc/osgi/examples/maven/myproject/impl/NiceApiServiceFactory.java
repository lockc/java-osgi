package lockc.osgi.examples.maven.myproject.impl;

import lockc.osgi.examples.maven.myproject.api.NiceApi;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;


public class NiceApiServiceFactory implements ServiceFactory<NiceApi> {

    @Override
    public NiceApi getService(Bundle bundle, ServiceRegistration<NiceApi> registration) {
            
        
        
        
        return new NiceApiImpl();
    }

    @Override
    public void ungetService(Bundle bundle, ServiceRegistration<NiceApi> registration, NiceApi service) {
    
        
    }
    
}
