package lockc.osgi.examples.springplugin.api;

import org.osgi.framework.BundleContext;

public class SpringBundleApplication {
    
    @SuppressWarnings("resource")
    public static void run(Class<?> source, BundleContext context) {
    
        OsgiAnnotationConfigApplicationContext ctx = 
                new OsgiAnnotationConfigApplicationContext(context, source);
        ServiceRegistry sr = ctx.getBean(ServiceRegistry.class);
        sr.init(context, source);
        
    }
 
}
