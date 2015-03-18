package lockc.osgi.examples.maven.myproject.integration;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static lockc.osgi.examples.maven.myproject.integration.Options.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import lockc.osgi.examples.maven.myproject.api.NiceApi;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class NiceApiServiceTest {
    
    @Inject
    private BundleContext bundleContext;
    
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
        };
        //@formatter:on
    }
    
    @Test
    public void checkInject() {
    
        assertNotNull(bundleContext);
        assertNotNull(niceApiService);
    }
    
    @Test
    public void testBundleActive() throws BundleException {
    
        Bundle b = getBundle(bundleContext, "lockc.osgi.examples.nice-api-bundle");
        assertEquals("Bundle state", Bundle.ACTIVE, b.getState());
    }
    
    @Test
    public void testNiceApiServiceGimmeSomeMoney() throws BundleException {
    
        int given = niceApiService.gimmeSomeMoney(300);
        assertEquals(400, given);
    }
    
    @Test
    public void testNiceApiServiceSayHello() throws BundleException {
    
        niceApiService.sayHello("Chris");
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
