package lockc.osgi.examples.springbundle;

import org.osgi.framework.BundleContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringBundleApplication {
    
    public static void run(Class<?> source, BundleContext context) {
    
        AnnotationConfigApplicationContext ctx = loadSpringContext(source);
        
        // initialise all services
        
        ServiceRegistry sr = ctx.getBean(ServiceRegistry.class);
        sr.init(context, source);
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
