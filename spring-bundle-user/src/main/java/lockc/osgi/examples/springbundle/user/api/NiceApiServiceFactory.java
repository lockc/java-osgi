package lockc.osgi.examples.springbundle.user.api;

import java.util.Dictionary;
import java.util.Objects;

import lockc.osgi.examples.springbundle.user.internal.NiceApiImpl;
import lockc.osgi.examples.springplugin.api.PropertyProvidingServiceFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class NiceApiServiceFactory implements PropertyProvidingServiceFactory<NiceApi>, ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    
    public NiceApiServiceFactory() {
    
        System.out.println("Contructed: " + this.getClass().getName());
    }
    
    @Override
    public NiceApi getService(Bundle bundle, ServiceRegistration<NiceApi> registration) {
    
        NiceApi api = applicationContext.getBean(NiceApi.class);
        
        if (Objects.isNull(api)) {
            return new NiceApiImpl();
        }
        
        return api;
    }
    
    @Override
    public void ungetService(Bundle bundle, ServiceRegistration<NiceApi> registration, NiceApi service) {
    
    }
    
    @Override
    public Dictionary<String, Object> properties() {
    
        return null;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    
        this.applicationContext = applicationContext;
    }
    
}
