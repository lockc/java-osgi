package lockc.osgi.examples.springbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
    
        // SpringBundle.run(this.getClass(), context);
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
    
    }
}
