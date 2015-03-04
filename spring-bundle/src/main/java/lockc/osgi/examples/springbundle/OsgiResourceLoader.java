package lockc.osgi.examples.springbundle;

import java.io.IOException;
import java.net.URL;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ReflectionUtils;

public class OsgiResourceLoader extends PathMatchingResourcePatternResolver {
    
    @Override
    protected Resource resolveRootDirResource(Resource original) throws IOException {

        URL url = original.getURL();
        if (url.getProtocol().startsWith("bundle")) {
            System.out.println(url.toString());
            
//            return new UrlResource("jar:file:/home/lockc/development/java-osgi/spring-bundle/felix-cache/bundle6/version0.0/bundle.jar!/");
            return new UrlResource("jar:file:/home/lockc/development/java-osgi/spring-bundle/felix-cache/bundle6/version0.0/bundle.jar!/lockc/osgi/examples/springbundle/");
            
        }
        
        return original;
    }
    
}
