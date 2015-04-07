package lockc.osgi.ddf.input.transformers.integration;

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Options {
    
    
    public static Option configureDistribution() {
    
        //@formatter:off
        return composite(
//                debugConfiguration(),
                karafDistributionConfiguration(maven("ddf.distribution", "ddf", "2.6.0")
                            .type("zip").getURL(), 
                        "ddf", "2.3.8")
                    .unpackDirectory(new File("target/exam"))
                    .useDeployFolder(false)
                );
        //@formatter:on
    }
    
    public static Option configurePaxExam() {
    
        //@formatter:off
        return composite(
                logLevel(LogLevelOption.LogLevel.INFO), 
                keepRuntimeFolder(), 
                useOwnExamBundlesStartLevel(100),
                // increase timeout for TravisCI
                systemTimeout(TimeUnit.MINUTES.toMillis(10)));
        //@formatter:on
    }
    
    public static Option configureConfigurationPorts() throws URISyntaxException {
    
        //@formatter:off
        return composite(
                editConfigurationFilePut("etc/system.properties", "urlScheme", "https"),
                editConfigurationFilePut("etc/system.properties", "host", "localhost"),
                editConfigurationFilePut("etc/system.properties", "jetty.port", HTTPS_PORT),
                editConfigurationFilePut("etc/system.properties", "hostContext", "/solr"),
                editConfigurationFilePut("etc/system.properties", "ddf.home", "${karaf.home}"),
                editConfigurationFilePut("etc/org.apache.karaf.shell.cfg", "sshPort", SSH_PORT),
                editConfigurationFilePut("etc/ddf.platform.config.cfg", "port", HTTP_PORT),
                editConfigurationFilePut("etc/org.ops4j.pax.web.cfg",
                        "org.osgi.service.http.port", HTTP_PORT),
                editConfigurationFilePut("etc/org.ops4j.pax.web.cfg",
                        "org.osgi.service.http.port.secure", HTTPS_PORT),
                editConfigurationFilePut("etc/org.apache.karaf.management.cfg",
                        "rmiRegistryPort", RMI_REG_PORT),
                editConfigurationFilePut("etc/org.apache.karaf.management.cfg",
                        "rmiServerPort", RMI_SERVER_PORT),
                        
                        
                        
                        
                replaceConfigurationFile(
                        "etc/hazelcast.xml", 
                        new File(Options.class.getResource("/hazelcast.xml").toURI())),
                        
//                replaceConfigurationFile("etc/ddf.security.sts.client.configuration.cfg",
//                        new File(this.getClass()
//                        .getResource("/ddf.security.sts.client.configuration.cfg").toURI())),
                
                replaceConfigurationFile(
                        "etc/ddf.catalog.solr.external.SolrHttpCatalogProvider.cfg",
                        new File(Options.class.getResource("/ddf.catalog.solr.external.SolrHttpCatalogProvider.cfg").toURI()))
            );
        //@formatter:on
    }
    
    public static Option configureMavenRepos() {
    
        //@formatter:off
        return composite(
                editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
                        "org.ops4j.pax.url.mvn.repositories",
                        "https://repo1.maven.org/maven2@id=central,"
                                + "https://oss.sonatype.org/content/repositories/snapshots@snapshots@noreleases@id=sonatype-snapshot,"
                                + "https://oss.sonatype.org/content/repositories/ops4j-snapshots@snapshots@noreleases@id=ops4j-snapshot,"
                                + "http://repository.apache.org/content/groups/snapshots-group@snapshots@noreleases@id=apache,"
                                + "http://svn.apache.org/repos/asf/servicemix/m2-repo@id=servicemix,"
                                + "http://repository.springsource.com/maven/bundles/release@id=springsource,"
                                + "http://repository.springsource.com/maven/bundles/external@id=springsourceext,"
                                + "http://oss.sonatype.org/content/repositories/releases/@id=sonatype"));
        //@formatter:on
    }
    
    public static Option configureSystemSettings() {
    
        //@formatter:off
        return composite(
                when(System.getProperty(TEST_LOGLEVEL_PROPERTY) != null).useOptions(
                        systemProperty(TEST_LOGLEVEL_PROPERTY)
                                .value(System.getProperty(TEST_LOGLEVEL_PROPERTY, ""))),
                when(Boolean.getBoolean("isDebugEnabled")).useOptions(
                        vmOption("-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005")),
                when(System.getProperty("maven.repo.local") != null).useOptions(
                        systemProperty("org.ops4j.pax.url.mvn.localRepository")
                                .value(System.getProperty("maven.repo.local", ""))));
        //@formatter:on
    }
    
    public static Option configureVmOptions() {
    
        //@formatter:off
        return composite(
                vmOption("-Xmx2048M"),
                vmOption("-XX:PermSize=128M"),
                vmOption("-XX:MaxPermSize=512M"));
        //@formatter:on
    }
    
    public static Option configureStartScript() {
    
        //@formatter:off
        return composite(
                editConfigurationFileExtend("etc/org.apache.karaf.features.cfg", "featuresBoot",
                        "catalog-app,solr-app"));
        //@formatter:on
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    public static final Logger LOGGER = LoggerFactory.getLogger(Options.class);
    
    public static final String LOG_CONFIG_PID = "org.ops4j.pax.logging";
    
    public static final String LOGGER_PREFIX = "log4j.logger.";
    
    public static final String KARAF_VERSION = "2.3.8";
    
    public static final int ONE_MINUTE_MILLIS = 60000;
    
    public static final int FIVE_MINUTES_MILLIS = ONE_MINUTE_MILLIS * 5;
    
    public static final String HTTP_PORT = "9081";
    
    public static final String HTTPS_PORT = "9993";
    
    public static final String SSH_PORT = "9101";
    
    public static final String RMI_SERVER_PORT = "44445";
    
    public static final String RMI_REG_PORT = "1100";
    
    public static final String SERVICE_ROOT = "https://localhost:" + HTTPS_PORT + "/services";
    
    public static final String REST_PATH = SERVICE_ROOT + "/catalog/";
    
    public static final String OPENSEARCH_PATH = REST_PATH + "query";
    
    public static final String DEFAULT_LOG_LEVEL = "TRACE";
    
    public String logLevel = "";
    
    public static final String TEST_LOGLEVEL_PROPERTY = "org.codice.test.defaultLoglevel";
}
