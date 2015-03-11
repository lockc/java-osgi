package lockc.osgi.examples.springplugin.api;

import org.osgi.framework.BundleContext;

public class SpringBundleApplication {
    
    @SuppressWarnings("resource")
    public static void run(Class<?> source, BundleContext context) {
    
        OsgiAnnotationConfigApplicationContext ctx = new OsgiAnnotationConfigApplicationContext(context, SpringBundleApplication.class);
        ServiceRegistry sr = ctx.getBean(ServiceRegistry.class);
        sr.init(context, source);
        
        
//        ClassLoader original = Thread.currentThread().getContextClassLoader();
//        OsgiAnnotationConfigApplicationContext ctx2 = null;
//        try {
//            Thread.currentThread().setContextClassLoader(SpringBundleApplication.class.getClassLoader());
//            
//            ctx2 = new OsgiAnnotationConfigApplicationContext(context);
//            
////            ctx2.setClassLoader(SpringBundleApplication.class.getClassLoader());
//            
//            ctx2.scan(SpringBundleApplication.class.getPackage().getName());
//            ctx2.refresh();
//        }catch(Exception e) {
//            e.printStackTrace();
//            return;
//        }
//        finally {
//            // Reset the thread's context class loader
//            Thread.currentThread().setContextClassLoader(original);
//        }
//        
//        // initialise all services
//        ctx2.setClassLoader(source.getClassLoader());  // ?? 
//        ServiceRegistry sr2 = ctx2.getBean(ServiceRegistry.class);
//        sr2.init(context, source);
        
    }
 
}
