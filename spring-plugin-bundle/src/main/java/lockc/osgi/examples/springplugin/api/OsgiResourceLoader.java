package lockc.osgi.examples.springplugin.api;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class OsgiResourceLoader extends PathMatchingResourcePatternResolver {
    
    private BundleContext bundleContext;
    
    public OsgiResourceLoader(BundleContext bundleContext) {
    
        this.bundleContext = bundleContext;
    }
    
    public OsgiResourceLoader(BundleContext bundleContext, ClassLoader classLoader) {
        super(classLoader);
        
        this.bundleContext = bundleContext;
    }
    
    @Override
    protected Resource resolveRootDirResource(Resource original) throws IOException {
    
        if (original.getURL().getProtocol().startsWith("bundle")) {
            
            System.out.println(original.toString());
            String baseUrl = original.getURL().toString();
            
            baseUrl = baseUrl.replace("bundle://", "");
            
            long bundleId = Long.parseLong(""+baseUrl.charAt(0));
            
            
            String classPath = baseUrl.substring(baseUrl.indexOf("/") + 1, baseUrl.length());
            
            
            Bundle bundle = bundleContext.getBundle(bundleId);
            System.out.println(bundle.getLocation());
            Path bundlePath = Paths.get(bundle.getLocation().replace("file:", ""));
            Path absPath = bundlePath.toAbsolutePath().normalize();
            
            String urlResource = "jar:file:" + absPath.toString() + "!/" + classPath;
            System.out.println(urlResource);
            return new UrlResource(urlResource);
        }
        
        return super.resolveRootDirResource(original);
    }
    
}
