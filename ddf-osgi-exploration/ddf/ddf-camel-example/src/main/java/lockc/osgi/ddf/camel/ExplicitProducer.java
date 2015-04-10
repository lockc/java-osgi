package lockc.osgi.ddf.camel;

import org.apache.camel.InOnly;


/**
 * Our producer that Camel will proxy 
 *
 */
public interface ExplicitProducer {
    
    @InOnly
    void sendMessage(String message);
}
