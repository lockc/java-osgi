package lockc.osgi.ddf.input.transformers.integration;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static lockc.osgi.ddf.input.transformers.integration.Options.*;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFileExtend;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.replaceConfigurationFile;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.useOwnExamBundlesStartLevel;

import java.io.File;
import java.net.URISyntaxException;

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

import ddf.catalog.transform.InputTransformer;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class SampleXmlInputTransformerServiceTest {
    
    @Inject
    private BundleContext bundleContext;
    
    @Inject
    private InputTransformer inputTransformerService;
    
    //http://artifacts.codice.org/content/repositories/releases/
    
    @Configuration
    public Option[] config1() throws Exception {
    
        //@formatter:off
        return new Option[] {
                cleanCaches(true),
                junitBundles(),
//                ariesBundles(),
//                logBundles(),
                configureDistribution(),
                configurePaxExam(),
                configureConfigurationPorts(),
                configureMavenRepos(),
                configureSystemSettings(),
                configureVmOptions(),
                configureStartScript(),
                
                
                
//                mavenBundle("lockc.osgi.ddf.examples", "ddf-xml-input-transformer", "1.0-SNAPSHOT"),
        };
        //@formatter:on
    }
    
    @Test
    public void checkInject() {
    
        assertNotNull(bundleContext);
        assertNotNull(inputTransformerService);
    }
    
    @Test
    public void testBundleActive() throws BundleException {
    
        Bundle b = getBundle(bundleContext, "lockc.osgi.ddf.examples.ddf-xml-input-transformer");
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
