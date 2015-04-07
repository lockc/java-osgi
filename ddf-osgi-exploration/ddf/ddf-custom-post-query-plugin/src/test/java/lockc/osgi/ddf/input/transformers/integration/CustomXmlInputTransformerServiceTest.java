package lockc.osgi.ddf.input.transformers.integration;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static lockc.osgi.ddf.input.transformers.integration.Options.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

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

import ddf.catalog.plugin.PostQueryPlugin;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class CustomXmlInputTransformerServiceTest {
    
    @Inject
    private BundleContext bundleContext;
    
    @Inject
    @Filter("(id=custom)")
    private PostQueryPlugin postQueryPluginService;
    
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
                
                mavenBundle("lockc.osgi.ddf.examples", "ddf-custom-post-query-plugin", "1.0-SNAPSHOT")
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
        assertNotNull(postQueryPluginService);
    }
    
    @Test
    public void testBundleActive() throws Exception {
    
        Bundle b = getBundle(bundleContext, "lockc.osgi.ddf.examples.ddf-custom-post-query-plugin");
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
