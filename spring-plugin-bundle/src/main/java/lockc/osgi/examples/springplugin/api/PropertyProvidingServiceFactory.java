package lockc.osgi.examples.springplugin.api;

import java.util.Dictionary;

import org.osgi.framework.ServiceFactory;


public interface PropertyProvidingServiceFactory<S> extends ServiceFactory<S> {
    
    Dictionary<String, ?> properties();
}
