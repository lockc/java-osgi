package lockc.osgi.ddf.input.transformers.integration;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static lockc.osgi.ddf.input.transformers.integration.Options.*;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import lockc.osgi.ddf.metacard.CustomMetacardTypeImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.apache.karaf.shell.osgi.BlueprintListener;
import org.apache.karaf.shell.osgi.BlueprintListener.BlueprintState;

import ddf.catalog.data.Metacard;
import ddf.catalog.transform.InputTransformer;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class CustomXmlInputTransformerServiceTest {
    
    @Inject
    private BundleContext bundleContext;
    
    /*
     * Inject our an input transformer, DDF has a few input transformers
     * so identify ours by the service property 'id' (see in the blueprint.xml)  
     */
    @Inject
    @Filter("(id=custom)")
    private InputTransformer inputTransformerService;
    
    private BlueprintListener blueprintListener;
    
    private boolean beforeRan = false;
        
    @Configuration
    public Option[] config() throws Exception {
    
        //@formatter:off
        return new Option[] {
                junitBundles(),
                configureDistribution(),
                configurePaxExam(),
                configureConfigurationPorts(),
                configureMavenRepos(),
                configureSystemSettings(),
                configureVmOptions(),
                configureStartScript(),
                
                mavenBundle("lockc.osgi.ddf.examples", "ddf-custom-metacard", "1.0-SNAPSHOT")
        };
        //@formatter:on
    }
    
    @Before
    public void beforeTest() throws Exception {

        if(!beforeRan) {
            /*
             * The @BeforeClass does not work in Pax Exam so use a flag for
             * one time set up tasks in the @Before method.
             */
            waitForAllBundles();
            
            beforeRan = true;
        }
        
    }

    @Test
    public void checkInject() throws Exception {
        assertNotNull(bundleContext);
        assertNotNull(inputTransformerService);
    }
    
    @Test
    public void testBundleActive() throws Exception {
    
        Bundle b = getBundle(bundleContext, "lockc.osgi.ddf.examples.ddf-custom-metacard");
        assertEquals("Bundle state", Bundle.ACTIVE, b.getState());
    }
    
    @Test
    public void testSampleXmlTransformerService() throws Exception {
        
        String xml = "<?xml version=\"1.0\"?>"
                + "<Something><Title>SCISYS Location</Title>"
                + "<Location>POINT(-2.142417 51.447569)</Location>"
                + "<ResourceURI>http://www.scisys.co.uk/</ResourceURI>"
                + "<CustomAttr>Wow!</CustomAttr>"
                + "<Metadata><SomeTag>Scisys Chippenham Office</SomeTag>"
                + "</Metadata></Something>";
        
        ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
        Metacard metacard = inputTransformerService.transform(in);
        
        assertEquals("Wow!", metacard.getAttribute(CustomMetacardTypeImpl.CUSTOM_ATTR).getValue());
        assertEquals("SCISYS Location", metacard.getTitle());
        assertEquals("<SomeTag>Scisys Chippenham Office</SomeTag>", metacard.getMetadata());
    }
    
    
    private Bundle getBundle(BundleContext _bundleContext, String symbolicName) {
    
        for (Bundle b : _bundleContext.getBundles()) {
            if (b.getSymbolicName().equals(symbolicName)) {
                return b;
            }
            
        }
        throw new IllegalArgumentException("Bundle not installed");
    }
    
    private void waitForAllBundles() throws Exception {
        waitForRequiredBundles("");
    }

    private void waitForRequiredBundles(String symbolicNamePrefix) throws Exception {
        boolean ready = false;
        if (blueprintListener == null) {
            blueprintListener = new BlueprintListener();
            bundleContext.registerService("org.osgi.service.blueprint.container.BlueprintListener",
                    blueprintListener, null);
        }

        long timeoutLimit = System.currentTimeMillis() + FIVE_MINUTES_MILLIS;
        while (!ready) {
            List<Bundle> bundles = Arrays.asList(bundleContext.getBundles());

            ready = true;
            for (Bundle bundle : bundles) {
                if (bundle.getSymbolicName().startsWith(symbolicNamePrefix)) {
                    String bundleName = bundle.getHeaders().get(Constants.BUNDLE_NAME);
                    String blueprintState = blueprintListener.getState(bundle);
                    if (blueprintState != null) {
                        if (BlueprintState.Failure.toString().equals(blueprintState)) {
                            fail("The blueprint for " + bundleName + " failed.");
                        } else if (!BlueprintState.Created.toString().equals(blueprintState)) {
                            LOGGER.info("{} blueprint not ready with state {}", bundleName,
                                    blueprintState);
                            ready = false;
                        }
                    }

                    if (!((bundle.getHeaders().get("Fragment-Host") != null
                            && bundle.getState() == Bundle.RESOLVED) || bundle
                            .getState() == Bundle.ACTIVE)) {
                        LOGGER.info("{} bundle not ready yet", bundleName);
                        ready = false;
                    }
                }
            }

            if (!ready) {
                if (System.currentTimeMillis() > timeoutLimit) {
                    fail("Bundles and blueprint did not start in time.");
                }
                LOGGER.info("Bundles not up, sleeping...");
                Thread.sleep(1000);
            }
        }
    }
}
