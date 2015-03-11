package lockc.osgi.examples.springplugin.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.osgi.framework.ServiceFactory;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisteredService {
    
    Class<? extends ServiceFactory<?>> serviceFactory();
    
}
