package lockc.osgi.examples.maven.myproject.integration;

import static org.ops4j.pax.exam.CoreOptions.*;

import org.ops4j.pax.exam.Option;

public class Options {
    
    public static Option ariesBundles() {
    
        //@formatter:off
        return composite(
                mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core", "1.4.3"),
                mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.api", "1.0.1"),
                mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.cm", "1.0.6"),
                mavenBundle("org.apache.aries", "org.apache.aries.util", "1.1.0"),
                mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy", "1.0.1"));
        //@formatter:on
    }
    
    public static Option logBundles() {
    
        //@formatter:off
        return composite(
                mavenBundle( "org.slf4j", "slf4j-api", "1.7.10" ),
                mavenBundle( "org.slf4j", "slf4j-log4j12", "1.7.10").noStart(),
                mavenBundle( "log4j", "log4j", "1.2.17" )
                );
        //@formatter:on
    }
    
}
