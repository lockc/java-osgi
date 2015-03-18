package lockc.osgi.examples.maven.myproject.user;

import lockc.osgi.examples.maven.myproject.api.NiceApi;
import lockc.osgi.examples.maven.myproject.impl.NiceApiImpl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


public class UserActivator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
    
        System.out.println("Starting Person API Bundle.");
        ServiceReference<NiceApi> serviceReference = context.getServiceReference(NiceApi.class);
        NiceApi niceAPi = context.getService(serviceReference);
        niceAPi.sayHello("Chris");
        int given = niceAPi.gimmeSomeMoney(200);
        System.out.println("you were given £" + given);
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
    
        System.out.println("Stopping Person API Bundle.");
    }
    
}

