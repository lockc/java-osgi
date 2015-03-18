package lockc.osgi.examples.springbundle.user.internal;

import org.springframework.stereotype.Component;

import lockc.osgi.examples.springplugin.api.ServiceRegistry;

@Component
public class ServiceRegistryImpl extends ServiceRegistry {
        
    public ServiceRegistryImpl() {
    
        System.out.println("Constructed: " + getClass().getName());
    }
}
