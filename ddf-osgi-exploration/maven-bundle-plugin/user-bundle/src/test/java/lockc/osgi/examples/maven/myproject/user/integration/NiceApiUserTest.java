package lockc.osgi.examples.maven.myproject.user.integration;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static lockc.osgi.examples.maven.myproject.user.integration.Options.*;

import javax.inject.Inject;

import lockc.osgi.examples.maven.myproject.api.NiceApi;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class NiceApiUserTest {
    
    @Inject
    private BundleContext bundleContext;
    
    /*
     * Injecting this causes Pax Exam to wait for the service to be registered, 
     * meaning in the logs you should see the 'hello Chris' text output. Not having
     * this means that Pax Exam would have finished running the tests before the OSGi 
     * runtime is fully initiated as the Blueprint DI is done asynchronously so tests
     * basically start before everything is wired. 
     */
    @Inject
    private NiceApi niceApiService;
        
    @Configuration
    public Option[] config1() {
    
        //@formatter:off
        return new Option[] {
                cleanCaches(true),
                junitBundles(),
                ariesBundles(),
                logBundles(),
                mavenBundle("lockc.osgi.examples", "nice-api-bundle", "1.0-SNAPSHOT"),
                mavenBundle("lockc.osgi.examples", "user-bundle", "1.0-SNAPSHOT"),
        };
        //@formatter:on
    }
    
    @Before
    public void before() {
        assertNotNull(bundleContext);
        assertNotNull(niceApiService);
    }
    
    @After
    public void after() {
        
    }
        
    @Test
    public void testBundleActive() throws BundleException {
        Bundle b = getBundle(bundleContext, "lockc.osgi.examples.user-bundle");
        assertEquals("Bundle state", Bundle.ACTIVE, b.getState());
    }
 
    
    private Bundle getBundle(BundleContext _bundleContext, String symbolicName) {
    
        for (Bundle b : _bundleContext.getBundles()) {
            if (b.getSymbolicName().equals(symbolicName)) {
                return b;
            }
            
        }
        throw new IllegalArgumentException("Bundle not installed");
    }
    
}
