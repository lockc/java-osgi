package lockc.osgi.examples.springplugin.api;

import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
//import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ServiceRegistry implements ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    
    public ServiceRegistry() {
    
        System.out.println("Constructed: " + this.getClass().getName());
    }
    
    public ServiceRegistry init(BundleContext context, Class<?> basePackageClass) {
        
        
        System.out.println("Registering services");
        
//        Reflections reflections = new Reflections(basePackageClass.getClassLoader());
//        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RegisteredService.class);
//        
//        for (Class<?> clazz : annotated) {
//            
//            if (clazz.isAnnotationPresent(RegisteredService.class)) {
//                
//                RegisteredService service = clazz.getAnnotation(RegisteredService.class);
//                Class<?> serviceFactory = service.serviceFactory();
//                
//                try {
//                    
//                    ServiceFactory<?> serviceFactoryBean = tryForBean(serviceFactory);
//                    if (Objects.isNull(serviceFactoryBean)) {
//                        System.out.println("No available bean of type " + serviceFactory.getName());
//                        serviceFactoryBean = (ServiceFactory<?>) serviceFactory.newInstance();
//                        System.out.println("Service factory created from Class<?>.newInstance method");
//                    }
//                    
//                    if(serviceFactoryBean instanceof PropertyProvidingServiceFactory) {
//                        PropertyProvidingServiceFactory<?> ppsf = (PropertyProvidingServiceFactory<?>) serviceFactoryBean;
//                        context.registerService(clazz.getName(), ppsf, ppsf.properties());
//                    } else {
//                        context.registerService(clazz.getName(), serviceFactoryBean, null);
//                    }
//                    
//                    context.registerService(clazz.getName(), serviceFactoryBean, null);
//                    System.out.println("Registered OSGi service " + clazz.getName());
//                }
//                catch (InstantiationException | IllegalAccessException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                
//            }
//            
//        }
        
        return this;
        
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    
        this.applicationContext = applicationContext;
    }
    
    
    private ServiceFactory<?> tryForBean(Class<?> clazz) {
        try {
            return (ServiceFactory<?>) applicationContext.getBean(clazz);
        } catch(NoSuchBeanDefinitionException ex) {
            return null;
        }
        
    }
    
}
