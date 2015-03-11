package lockc.osgi.examples.springplugin.api;

import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

public class OsgiAnnotationConfigApplicationContext extends GenericApplicationContext implements
        AnnotationConfigRegistry {
    
    private AnnotatedBeanDefinitionReader reader;
    
    private ClassPathBeanDefinitionScanner scanner;
    
    private ResourcePatternResolver resourceLoader;
    
    public OsgiAnnotationConfigApplicationContext(BundleContext bundleContext, Class<?> clazz) {
    
        ClassLoader original = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            
            resourceLoader = new OsgiResourceLoader(bundleContext, getClass().getClassLoader());
            
            setResourceLoader(resourceLoader);
            
            setClassLoader(getClass().getClassLoader());
            
            reader = new AnnotatedBeanDefinitionReader(this);
            scanner = new ClassPathBeanDefinitionScanner(this);
            scanner.setResourceLoader(resourceLoader);
            
            
            
            scan(this.getClass().getPackage().getName());
            refresh();
            
            
            
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        finally {
            // Reset the thread's context class loader
            Thread.currentThread().setContextClassLoader(original);
        }
    }
    
    @Override
    public void register(Class<?>... annotatedClasses) {
    
        Assert.notEmpty(annotatedClasses, "At least one annotated class must be specified");
        this.reader.register(annotatedClasses);
    }
    
    @Override
    public void scan(String... basePackages) {
    
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        this.scanner.scan(basePackages);
    }
    
    @Override
    protected void prepareRefresh() {
    
        this.scanner.clearCache();
        super.prepareRefresh();
    }
    
    @Override
    protected ResourcePatternResolver getResourcePatternResolver() {
    
        if (Objects.nonNull(this.resourceLoader)) {
            return resourceLoader;
        }
        else {
            return super.getResourcePatternResolver();
        }
    }
    
}
