package lockc.osgi.ddf.camel;

import java.io.IOException;

import org.apache.camel.Produce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddf.catalog.data.BinaryContent;
import ddf.catalog.data.Metacard;
import ddf.catalog.event.DeliveryMethod;
import ddf.catalog.transform.CatalogTransformerException;
import ddf.catalog.transform.MetacardTransformer;

public class ExplicitContentDeliveryMethod implements DeliveryMethod {
    
    private static final Logger LOG = LoggerFactory.getLogger(ExplicitContentDeliveryMethod.class);
        
    /**
     * Injected by the Camel context which is resolved from the blueprint context,
     * This will kick off the Camel routing when a Metacard is created
     */
    @Produce(uri = "direct:starter-for-ten")
    private ExplicitProducer producer;
    
    private MetacardTransformer transformer;
    
    public void setTransformer(MetacardTransformer transformer) {
    
        this.transformer = transformer;
    }

    @Override
    public void created(Metacard newMetacard) {
    
        LOG.info(">>>>> created metacard with title of : " + newMetacard.getTitle());
        
        try {
            BinaryContent content = transformer.transform(newMetacard, null);
            String xml = new String(content.getByteArray());
            
            if(producer != null) {
                producer.sendMessage(xml);
            } else {
                LOG.warn("Cannot initiate Camel routing because no producer has been supplied to the delivery method.");
            }
        }
        catch (CatalogTransformerException | IOException e) {
            LOG.error("Error occurred initiating the Camel route.", e);
        }
    }
    
    @Override
    public void deleted(Metacard oldMetacard) {
    
        LOG.info(">>>>> deleted metacard with title of : " + oldMetacard.getTitle());
    }
    
    @Override
    public void updatedHit(Metacard newMetacard, Metacard oldMetacard) {
    
        LOG.info(">>>>> updatedHit. metacard title : " + oldMetacard.getTitle());
    }
    
    @Override
    public void updatedMiss(Metacard newMetacard, Metacard oldMetacard) {
    
        LOG.info(">>>>> updatedMiss. metacard title : " + oldMetacard.getTitle());
    }
    
}
