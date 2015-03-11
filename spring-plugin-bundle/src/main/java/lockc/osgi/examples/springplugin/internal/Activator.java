package lockc.osgi.examples.springplugin.internal;

import lockc.osgi.examples.springplugin.api.SpringBundleApplication;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        
        System.out.println("Starting " + this.getClass().getName());
        
//        SpringBundleApplication.run(TestClass.class, context);
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
    
        System.out.println("Stopping " + this.getClass().getName());
    }
}
