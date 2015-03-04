package lockc.osgi.examples.springbundle;

import lockc.osgi.examples.springbundle.api.NiceApi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringBundle {
    
    public static void run(Class<?> source, BundleContext context) {
    
        AnnotationConfigApplicationContext ctx = loadSpringContext(source);
        
        // initialise all services
        
        ServiceRegistry sr = ctx.getBean(ServiceRegistry.class);
        sr.init(context, source);
        
        ServiceReference<NiceApi> serviceRef = context.getServiceReference(NiceApi.class);
        context.getService(serviceRef).sayHello("Chris");
    }
    
    private static AnnotationConfigApplicationContext loadSpringContext(Class<?> source) {
    
        ClassLoader original = Thread.currentThread().getContextClassLoader();
        AnnotationConfigApplicationContext ctx = null;
        try {
            Thread.currentThread().setContextClassLoader(source.getClassLoader());
            ctx = new AnnotationConfigApplicationContext();
            ctx.setResourceLoader(new OsgiResourceLoader());
            ctx.scan(source.getPackage().getName());
            ctx.refresh();
        }
        finally {
            // Reset the thread's context class loader
            Thread.currentThread().setContextClassLoader(original);
        }
        
        return ctx;
    }
    
}
